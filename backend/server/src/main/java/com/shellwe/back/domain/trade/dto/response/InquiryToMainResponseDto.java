package com.shellwe.back.domain.trade.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InquiryToMainResponseDto {

    private List<MainPageResponseDto> shells;
}
