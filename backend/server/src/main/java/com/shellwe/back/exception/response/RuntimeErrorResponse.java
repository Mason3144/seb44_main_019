package com.shellwe.back.exception.response;

import lombok.Getter;

@Getter
public class RuntimeErrorResponse {

    private String errorCode;
    private String errorMessage;

    public RuntimeErrorResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}