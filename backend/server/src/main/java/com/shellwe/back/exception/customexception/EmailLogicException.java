package com.shellwe.back.exception.customexception;

import com.shellwe.back.exception.exceptioncode.EmailExceptionCode;
import lombok.Getter;

@Getter
public class EmailLogicException extends RuntimeException {

    private EmailExceptionCode emailExceptionCode;

    public EmailLogicException(EmailExceptionCode emailExceptionCode) {
        super(emailExceptionCode.getMessage());
        this.emailExceptionCode = emailExceptionCode;
    }
}
