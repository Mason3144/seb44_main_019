package com.shellwe.back.exception.customexception;

import com.shellwe.back.exception.exceptioncode.TradeExceptionCode;
import lombok.Getter;

@Getter
public class TradeLogicException extends RuntimeException {

    private TradeExceptionCode tradeExceptionCode;

    public TradeLogicException(TradeExceptionCode tradeExceptionCode) {
        super(tradeExceptionCode.getMessage());
        this.tradeExceptionCode = tradeExceptionCode;
    }
}
