package com.shellwe.back.exception.customexception;

import com.shellwe.back.exception.exceptioncode.FileUploadExceptionCode;
import lombok.Getter;

@Getter
public class FileUploadLogicException extends RuntimeException {

    private FileUploadExceptionCode fileUploadExceptionCode;

    public FileUploadLogicException(FileUploadExceptionCode fileUploadExceptionCode) {
        super(fileUploadExceptionCode.getMessage());
        this.fileUploadExceptionCode = fileUploadExceptionCode;
    }
}
