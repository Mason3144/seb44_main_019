package com.shellwe.back.exception.customexception;

import com.shellwe.back.exception.exceptioncode.CartExceptionCode;
import lombok.Getter;

@Getter
public class CartLogicException extends RuntimeException {

    private CartExceptionCode cartExceptionCode;

    public CartLogicException(CartExceptionCode cartExceptionCode) {
        super(cartExceptionCode.getMessage());
        this.cartExceptionCode = cartExceptionCode;
    }
}

