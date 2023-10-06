package com.shellwe.back.exception.exceptioncode;

import lombok.Getter;

@Getter
public enum EmailExceptionCode {

    EMAIL_NOT_SEND(400, "Email Send Failed, Invalid email");

    private int status;

    private String message;

    EmailExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
