package com.shellwe.back.auth.sevice;

import com.shellwe.back.repository.MemberRepository;
import com.shellwe.back.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Transactional
@Service
public class OAuthMemberService {

    private final MemberRepository memberRepository;

    public OAuthMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member oauthFindMemberByEmail(String email) {
        log.info("find member by email in oauth layer start, email : {}", email);
        return findByEmail(email);
    }

    public Member oauthSignUpMember(Member member) {
        log.info("sign-up in service layer by oauth start, member : {}", member);

        Optional<Member> optionalMember = memberRepository.findByEmail(member.getEmail());

        if (!optionalMember.isPresent()) {
            return memberRepository.save(member);
        }

        Member existMember = optionalMember.get();
        if (existMember.getPassword() == null) {
            existMember.updateOauth(member.getDisplayName(), member.getProfileUrl());
            return memberRepository.save(existMember);
        } else if (existMember.getPassword() != null) {
            return null;
        }
        return null;
    }

    private Optional<Member> findByEmailAllowNull(String email) {
        Optional<Member> byEmail = memberRepository.findByEmail(email);
        return byEmail;
    }

    private Member findByEmail(String email) {
        Optional<Member> byEmail = memberRepository.findByEmail(email);
        Member member = byEmail.orElseThrow(() -> new IllegalStateException());
        return member;
    }

    private boolean verifyExistEmailByOauth(String email) {
        boolean exist = false;
        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isPresent()) {
            exist = true;
        }
        return exist;
    }
}
