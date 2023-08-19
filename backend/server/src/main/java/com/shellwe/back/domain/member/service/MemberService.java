package com.shellwe.back.domain.member.service;

import com.shellwe.back.domain.member.dto.request.DeleteRequestDto;
import com.shellwe.back.domain.member.dto.request.SignUpRequestDto;
import com.shellwe.back.domain.member.dto.request.UpdateRequestDto;
import com.shellwe.back.domain.member.dto.response.FindResponseDtoIncludeOauth;
import com.shellwe.back.domain.member.dto.response.GetMyShellListDto;
import com.shellwe.back.domain.member.dto.response.GetMyShellListDtoTags;
import com.shellwe.back.entity.Member;
import com.shellwe.back.domain.member.mapper.MemberMapper;
import com.shellwe.back.repository.MemberRepository;
import com.shellwe.back.entity.Shell;
import com.shellwe.back.entity.type.Status;
import com.shellwe.back.email.EmailSendable;
import com.shellwe.back.exception.customexception.EmailLogicException;
import com.shellwe.back.exception.customexception.MemberLogicException;
import com.shellwe.back.exception.exceptioncode.EmailExceptionCode;
import com.shellwe.back.exception.exceptioncode.MemberExceptionCode;
import com.shellwe.back.file.service.UploadPictureService;
import com.shellwe.back.utils.event.MemberRemoveEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailSendable emailSendable;
    private final UploadPictureService uploadPictureService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public MemberService(MemberRepository memberRepository, MemberMapper memberMapper,
                         PasswordEncoder passwordEncoder, EmailSendable emailSendable,
                         UploadPictureService uploadPictureService, ApplicationEventPublisher eventPublisher) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
        this.emailSendable = emailSendable;
        this.uploadPictureService = uploadPictureService;
        this.eventPublisher = eventPublisher;
    }

    public void signUpMember(SignUpRequestDto signUpRequestDto) throws InterruptedException {
        Member member = memberMapper.signUpRequestDtoToMember(signUpRequestDto);
        log.info("sign-up in service layer start, member : {}", member);
        verifyExistEmail(member.getEmail());

        Member encryptedMember = new Member(member, passwordEncoder.encode(member.getPassword()));
        memberRepository.save(encryptedMember);

        new Thread(() -> {
            try {
                emailSendable.send(new String[]{member.getEmail()}, "ShellWe 회원가입 인증",
                        member.getEmail(), "email-registration-member");
            } catch (InterruptedException e) {
                throw new EmailLogicException(EmailExceptionCode.EMAIL_NOT_SEND);
            }
        }).start();
        log.info("sent email and sign-up in service layer done");
    }

    public void verifyEmail(String email) {
        Member findMember = findByEmail(email);

        findMember.emailVerificationCompleted();
        memberRepository.save(findMember);

        log.info("email verification completed");
    }

    @Transactional(readOnly = true)
    public FindResponseDtoIncludeOauth findMemberById(Long contextId, long memberId) {
        log.info("find member in service layer by Id start, memberId : {}", memberId);
        FindResponseDtoIncludeOauth findResponseDtoIncludeOauth = new FindResponseDtoIncludeOauth();

        if (contextId == null || memberId != contextId) {
            Member findMember = findById(memberId);
            findResponseDtoIncludeOauth.setIsMeIdName(false, findMember.getId(),
                    findMember.getDisplayName(), findMember.getProfileUrl(), findMember.getIntroduction());
            return findResponseDtoIncludeOauth;
        }
        if (memberId == contextId) {
            Member findMember = findById(memberId);
            findResponseDtoIncludeOauth.setIsMeIdName(true, findMember.getId(),
                    findMember.getDisplayName(), findMember.getProfileUrl(), findMember.getIntroduction());
            if (findMember.isPasswordNull()) {
                findResponseDtoIncludeOauth.setOauthUser(true);
            }
            return findResponseDtoIncludeOauth;
        }

        return findResponseDtoIncludeOauth;
    }

    public void updateMember(long contextId, long memberId, UpdateRequestDto updateRequestDto, MultipartFile picture) {
        log.info("update member in service layer start");
        String profileUrl = null;

        if (memberId == contextId) {
            Member findMember = findById(memberId);
            if (findMember.getPassword() != null) {
                if (picture != null && !picture.isEmpty()) {
                    profileUrl = uploadPictureService.onePictureFileToUrl(picture);
                }
                findMember.updateMember(updateRequestDto.getPassword(), passwordEncoder,
                        updateRequestDto.getDisplayName(), updateRequestDto.getIntroduction(), profileUrl);
                memberRepository.save(findMember);
            } else {
                throw new MemberLogicException((MemberExceptionCode.FAILED_UPDATE_MEMBER_OAUTH_USER));
            }
        } else {
            throw new MemberLogicException(MemberExceptionCode.MEMBER_NOT_MY_ID);
        }

        log.info("update member in service layer end");
    }

    public void deleteMember(long contextId, long memberId, DeleteRequestDto deleteRequestDto) {
        log.info("delete member in service layer start");
        Member findMember = findById(contextId);

        if (memberId == contextId && passwordEncoder.matches(deleteRequestDto.getPassword(), findMember.getPassword())) {
            List<Long> shellIds = findMember.getShells().stream()
                    .map(Shell::getId)
                    .collect(Collectors.toList());
            
            eventPublisher.publishEvent(new MemberRemoveEvent(memberId, shellIds));
            memberRepository.delete(findMember);
        } else {
            throw new MemberLogicException(MemberExceptionCode.MEMBER_NOT_MY_ID);
        }

        log.info("delete member in service layer end");
    }

    @Transactional(readOnly = true)
    public Member getMemberByOtherLayer(long memberId) {
        return findById(memberId);
    }

    @Transactional(readOnly = true)
    public GetMyShellListDto myShellList(long memberId, Status status, long contextId) {
        GetMyShellListDto getMyShellListDto = new GetMyShellListDto();
        if (memberId == contextId) {
            Member member = findById(memberId);
            if (status == Status.ACTIVE) {
                List<Shell> activeShellList = member.getActiveList();
                getMyShellListDto.setShells(memberMapper.shellListToGetMyShellListDto(activeShellList));
            }
            if (status == Status.INACTIVE) {
                List<Shell> activeShellList = member.getInActiveList();
                getMyShellListDto.setShells(memberMapper.shellListToGetMyShellListDto(activeShellList));
            }
        } else {
            throw new MemberLogicException(MemberExceptionCode.MEMBER_NOT_MY_ID);
        }
        return getMyShellListDto;
    }

    @Transactional(readOnly = true)
    public GetMyShellListDtoTags myShellListUnAuthentication(long memberId, Status status) {
        GetMyShellListDtoTags getMyShellListDtoTags = new GetMyShellListDtoTags();

        Member member = findById(memberId);
        if (status == Status.ACTIVE) {
            List<Shell> activeShellList = member.getActiveList();
            getMyShellListDtoTags.setShells(memberMapper.shellListToGetMyShellListDtoTags(activeShellList));
        }
        if (status == Status.INACTIVE) {
            List<Shell> activeShellList = member.getInActiveList();
            getMyShellListDtoTags.setShells(memberMapper.shellListToGetMyShellListDtoTags(activeShellList));
        }
        return getMyShellListDtoTags;
    }

    private Member findByEmail(String email) {
        Optional<Member> byEmail = memberRepository.findByEmail(email);
        Member member = byEmail.orElseThrow(() -> new MemberLogicException(MemberExceptionCode.FAILED_FIND_BY_EMAIL));
        return member;
    }

    private Member findById(long memberId) {
        Optional<Member> byId = memberRepository.findById(memberId);
        Member member = byId.orElseThrow(() -> new MemberLogicException(MemberExceptionCode.FAILED_FIND_BY_ID));
        return member;
    }

    private void verifyExistEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isPresent()) {
            throw new MemberLogicException(MemberExceptionCode.EMAIL_DUPLICATED);
        }
    }
}
