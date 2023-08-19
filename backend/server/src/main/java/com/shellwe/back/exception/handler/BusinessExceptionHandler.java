package com.shellwe.back.exception.handler;

import com.shellwe.back.exception.customexception.*;
import com.shellwe.back.exception.response.BusinessLogicErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BusinessExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public BusinessLogicErrorResponse handleEmailLogicException(EmailLogicException emailLogicException) {
        log.error("email send logic exception", emailLogicException);
        return BusinessLogicErrorResponse.of(emailLogicException);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public BusinessLogicErrorResponse handleFileUploadLogicException(FileUploadLogicException fileUploadLogicException) {
        log.error("file upload logic exception", fileUploadLogicException);
        return BusinessLogicErrorResponse.of(fileUploadLogicException);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public BusinessLogicErrorResponse handleMemberLogicException(MemberLogicException memberLogicException) {
        log.error("member logic exception", memberLogicException);
        return BusinessLogicErrorResponse.of(memberLogicException);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public BusinessLogicErrorResponse handleShellLogicException(ShellLogicException shellLogicException) {
        log.error("email send exception", shellLogicException);
        return BusinessLogicErrorResponse.of(shellLogicException);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public BusinessLogicErrorResponse handleTradeLogicException(TradeLogicException tradeLogicException) {
        log.error("trade logic exception", tradeLogicException);
        return BusinessLogicErrorResponse.of(tradeLogicException);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public BusinessLogicErrorResponse handleAccessTokenException(AccessTokenException accessTokenException) {
        log.error("access token exception", accessTokenException);
        return BusinessLogicErrorResponse.of(accessTokenException);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public BusinessLogicErrorResponse handleCartLogicException(CartLogicException cartLogicException) {
        log.error("cart logic exception", cartLogicException);
        return BusinessLogicErrorResponse.of(cartLogicException);
    }
}