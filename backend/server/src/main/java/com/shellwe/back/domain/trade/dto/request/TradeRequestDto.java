package com.shellwe.back.domain.trade.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeRequestDto {

    private Long buyerShellId;

    private Long sellerShellId;
}
