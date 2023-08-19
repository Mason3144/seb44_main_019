package com.shellwe.back.exception.exceptioncode;

import lombok.Getter;

@Getter
public enum ShellExceptionCode {

    SHELL_NOT_MY_ID(400, "Shell Id Error"),
    NOT_SUPPORT_TYPE(400, "Shell Sort Type Error");

    private int status;

    private String message;

    ShellExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
