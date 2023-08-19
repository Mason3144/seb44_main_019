package com.shellwe.back.exception.customexception;

import com.shellwe.back.exception.exceptioncode.MemberExceptionCode;
import lombok.Getter;

@Getter
public class MemberLogicException extends RuntimeException {

    private MemberExceptionCode memberExceptionCode;

    public MemberLogicException(MemberExceptionCode memberExceptionCode) {
        super(memberExceptionCode.getMessage());
        this.memberExceptionCode = memberExceptionCode;
    }
}
