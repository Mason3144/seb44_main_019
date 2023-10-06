package com.shellwe.back.exception.customexception;

import com.shellwe.back.exception.exceptioncode.ShellExceptionCode;
import lombok.Getter;

@Getter
public class ShellLogicException extends RuntimeException {

    private ShellExceptionCode shellExceptionCode;

    public ShellLogicException(ShellExceptionCode shellExceptionCode) {
        super(shellExceptionCode.getMessage());
        this.shellExceptionCode = shellExceptionCode;
    }
}
