package com.shellwe.back.domain.member.controller;

import com.shellwe.back.auth.memberDetails.MemberContextInform;
import com.shellwe.back.domain.member.dto.request.DeleteRequestDto;
import com.shellwe.back.domain.member.dto.request.SignUpRequestDto;
import com.shellwe.back.domain.member.dto.request.UpdateRequestDto;
import com.shellwe.back.domain.member.dto.response.FindResponseDtoIncludeOauth;
import com.shellwe.back.domain.member.service.MemberService;
import com.shellwe.back.exception.customexception.AccessTokenException;
import com.shellwe.back.exception.exceptioncode.AccessTokenExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RequestMapping("/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @Value("${redirect.email-verification-success-url}")
    private String emailRedirectUrl;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void signUpMember(@Valid @RequestBody SignUpRequestDto signUpRequestDto) throws InterruptedException {
        memberService.signUpMember(signUpRequestDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/email/{email}")
    public void verificationMember(@PathVariable String email, HttpServletResponse response) throws IOException {
        memberService.verifyEmail(email);
        response.sendRedirect(emailRedirectUrl);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{memberId}")
    public FindResponseDtoIncludeOauth getMemberById(@PathVariable long memberId, Authentication authentication) {
        return memberService.findMemberById(getIdAllowNull(authentication), memberId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{memberId}")
    public void updateMemberById(@PathVariable long memberId,
                                 @Valid @RequestPart(value = "update") UpdateRequestDto updateRequestDto,
                                 @RequestPart(value = "picture", required = false) MultipartFile picture,
                                 Authentication authentication) {
        memberService.updateMember(getId(authentication), memberId, updateRequestDto, picture);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{memberId}")
    public void deleteMemberByIdAndPassword(@PathVariable long memberId,
                                            @RequestBody DeleteRequestDto deleteRequestDto,
                                            Authentication authentication) {
        memberService.deleteMember(getId(authentication), memberId, deleteRequestDto);
    }

    private Long getId(Authentication authentication) {
        Long id;
        if (authentication == null) {
            throw new AccessTokenException(AccessTokenExceptionCode.TOKEN_EXPIRED);
        } else {
            MemberContextInform memberInform = (MemberContextInform) authentication.getPrincipal();
            id = memberInform.getId();
        }
        return id;
    }

    private Long getIdAllowNull(Authentication authentication) {
        Long id;
        if (authentication == null) {
            id = null;
        } else {
            MemberContextInform memberInform = (MemberContextInform) authentication.getPrincipal();
            id = memberInform.getId();
        }
        return id;
    }
}
