package com.shellwe.back.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseDto {
    private String roomsUrl;
    private String roomUrl;
}
