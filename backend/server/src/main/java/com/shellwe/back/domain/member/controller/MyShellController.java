package com.shellwe.back.domain.member.controller;

import com.shellwe.back.auth.memberDetails.MemberContextInform;
import com.shellwe.back.domain.member.dto.response.GetMyShellListDto;
import com.shellwe.back.domain.member.dto.response.GetMyShellListDtoTags;
import com.shellwe.back.domain.member.service.MemberService;
import com.shellwe.back.entity.type.Status;
import com.shellwe.back.exception.customexception.AccessTokenException;
import com.shellwe.back.exception.exceptioncode.AccessTokenExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/myshell")
@RestController
public class MyShellController {

    private final MemberService memberService;

    @Autowired
    public MyShellController(MemberService memberService) {
        this.memberService = memberService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{memberId}/myshells")
    public GetMyShellListDto getMyShellList(@PathVariable long memberId,
                                            @RequestParam(value = "status") String status,
                                            Authentication authentication) {
        return memberService.myShellList(memberId, Status.valueOf(status.toUpperCase()), getId(authentication));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{memberId}/shells")
    public GetMyShellListDtoTags getShellListNotAuthentication(@PathVariable long memberId,
                                                               @RequestParam(value = "status") String status) {
        return memberService.myShellListUnAuthentication(memberId, Status.valueOf(status.toUpperCase()));
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
}
