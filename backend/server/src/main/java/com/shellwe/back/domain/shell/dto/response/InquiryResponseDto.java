package com.shellwe.back.domain.shell.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InquiryResponseDto {

    private List<ShellResponseDto> shells;
}
