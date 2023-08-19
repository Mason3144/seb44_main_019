package com.shellwe.back.exception.exceptioncode;

import lombok.Getter;

@Getter
public enum CartExceptionCode {

    CART_ALREADY_EXISTS(400, "Cart Duplicated"),
    CART_DELETE_FAILED(400, "Cart Delete Failed");

    private int status;

    private String message;

    CartExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
