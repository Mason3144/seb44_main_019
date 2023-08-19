package com.shellwe.back.domain.trade.dto.request;

import com.shellwe.back.entity.type.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTradeStatusRequestDto {

    private Status status;
}
