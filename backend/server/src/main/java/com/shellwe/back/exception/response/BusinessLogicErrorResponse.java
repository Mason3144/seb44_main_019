package com.shellwe.back.exception.response;

import com.shellwe.back.exception.customexception.*;
import lombok.Getter;

@Getter
public class BusinessLogicErrorResponse {

    private Error error;

    private BusinessLogicErrorResponse(Error error) {
        this.error = error;
    }

    public static BusinessLogicErrorResponse of(FileUploadLogicException fileUploadLogicException) {
        return new BusinessLogicErrorResponse(Error.of(fileUploadLogicException));
    }

    public static BusinessLogicErrorResponse of(EmailLogicException emailLogicException) {
        return new BusinessLogicErrorResponse(Error.of(emailLogicException));
    }

    public static BusinessLogicErrorResponse of(MemberLogicException memberLogicException) {
        return new BusinessLogicErrorResponse(Error.of(memberLogicException));
    }

    public static BusinessLogicErrorResponse of(ShellLogicException shellLogicException) {
        return new BusinessLogicErrorResponse(Error.of(shellLogicException));
    }

    public static BusinessLogicErrorResponse of(TradeLogicException tradeLogicException) {
        return new BusinessLogicErrorResponse(Error.of(tradeLogicException));
    }

    public static BusinessLogicErrorResponse of(AccessTokenException accessTokenException) {
        return new BusinessLogicErrorResponse(Error.of(accessTokenException));
    }

    public static BusinessLogicErrorResponse of(CartLogicException cartLogicException) {
        return new BusinessLogicErrorResponse(Error.of(cartLogicException));
    }

    @Getter
    public static class Error {

        private String errorName;
        private int status;

        private Error(String errorName, int status) {
            this.errorName = errorName;
            this.status = status;
        }

        public static Error of(FileUploadLogicException fileUploadLogicException) {
            return new Error(fileUploadLogicException.getMessage(), fileUploadLogicException.getFileUploadExceptionCode().getStatus());
        }

        public static Error of(EmailLogicException emailLogicException) {
            return new Error(emailLogicException.getMessage(), emailLogicException.getEmailExceptionCode().getStatus());
        }

        public static Error of(MemberLogicException memberLogicException) {
            return new Error(memberLogicException.getMessage(), memberLogicException.getMemberExceptionCode().getStatus());
        }

        public static Error of(ShellLogicException shellLogicException) {
            return new Error(shellLogicException.getMessage(), shellLogicException.getShellExceptionCode().getStatus());
        }

        public static Error of(TradeLogicException tradeLogicException) {
            return new Error(tradeLogicException.getMessage(), tradeLogicException.getTradeExceptionCode().getStatus());
        }

        public static Error of(AccessTokenException accessTokenException) {
            return new Error(accessTokenException.getMessage(), accessTokenException.getAccessTokenExceptionCode().getStatus());
        }

        public static Error of(CartLogicException cartLogicException) {
            return new Error(cartLogicException.getMessage(), cartLogicException.getCartExceptionCode().getStatus());
        }
    }
}
