package com.shellwe.back.domain.trade.dto.response;

import com.shellwe.back.domain.member.dto.response.MemberDtoExceptIsMe;
import com.shellwe.back.domain.picture.dto.PictureResponseDto;
import com.shellwe.back.entity.type.ShellType;
import com.shellwe.back.entity.type.Status;
import com.shellwe.back.entity.ShellCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShellForMyTradeResponseDetails {

    private Long id;

    private ShellType type;

    private Status status;

    private String title;

    private String body;

    private String createdAt;

    private ShellCategory category;

    private List<PictureResponseDto> pictures;

    private MemberDtoExceptIsMe member;
}
