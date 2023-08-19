package com.shellwe.back.exception.businessLogicException;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    LOGIN_NEEDED(403, "Authorization token missing "),
    ROOM_NOT_FOUND(404, "Room not found"),
    MEMBER_ROOM_NOT_FOUND(404, "MemberRoom not found"),
    MEMBER_ROOM_EXISTS(400, "MemberRoom exists"),
    TRADE_NOT_FOUND(400,"Trade not found"),
    SHELL_NOT_FOUND(400,"Shell not found"),
    WRONG_ROOM_NUMBER(404, "Wrong room number ");
    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
