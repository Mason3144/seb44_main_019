package com.shellwe.back.exception.exceptioncode;

import lombok.Getter;

@Getter
public enum FileUploadExceptionCode {

    FILE_CONVERT_ERROR(400, "File Convert Failed"),
    NOT_SUPPORTED_FILE_FORM(400, "Not Support File Type");

    private int status;

    private String message;

    FileUploadExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
