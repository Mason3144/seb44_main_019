package com.shellwe.server.exception.exceptioncode;

import lombok.Getter;

@Getter
public enum MemberExceptionCode {

    MEMBER_NOT_MY_ID(400, "Member Id Error"),
    FAILED_FIND_BY_EMAIL(400, "Not Found By Email"),
    FAILED_FIND_BY_ID(400,"Not Found By Id");

    private int status;

    private String message;

    MemberExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
