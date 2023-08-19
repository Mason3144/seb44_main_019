package com.shellwe.back.exception.exceptioncode;

import lombok.Getter;

@Getter
public enum MemberExceptionCode {

    MEMBER_NOT_MY_ID(400, "Member Id Error"),
    EMAIL_DUPLICATED(400, "이미 가입된 이메일 입니다."),
    FAILED_FIND_BY_EMAIL(400, "Not Found By Email"),
    FAILED_FIND_BY_ID(400,"Not Found By Id"),
    FAILED_UPDATE_MEMBER_OAUTH_USER(400, "Failed Update OAuth User"),
    FAILED_SIGN_UP_OAUTH(400, "Failed Signup OAuth");

    private int status;

    private String message;

    MemberExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
