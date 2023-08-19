package com.shellwe.back.exception.customexception;

import com.shellwe.back.exception.exceptioncode.AccessTokenExceptionCode;
import lombok.Getter;

@Getter
public class AccessTokenException extends RuntimeException {

    private AccessTokenExceptionCode accessTokenExceptionCode;

    public AccessTokenException(AccessTokenExceptionCode accessTokenExceptionCode) {
        super(accessTokenExceptionCode.getMessage());
        this.accessTokenExceptionCode = accessTokenExceptionCode;
    }
}
