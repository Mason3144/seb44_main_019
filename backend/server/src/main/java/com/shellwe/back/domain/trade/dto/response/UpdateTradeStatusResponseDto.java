package com.shellwe.back.domain.trade.dto.response;

import com.shellwe.back.domain.member.dto.response.FindResponseDto;
import com.shellwe.back.domain.picture.dto.PictureResponseDto;
import com.shellwe.back.domain.tag.dto.TagResponseDto;
import com.shellwe.back.entity.type.ShellType;
import com.shellwe.back.entity.type.Status;
import com.shellwe.back.entity.ShellCategory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class UpdateTradeStatusResponseDto {

    private Long id;

    private ShellType type;

    private String title;

    private List<PictureResponseDto> pictures;

    private String body;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private ShellCategory category;

    private List<TagResponseDto> tags;

    private Status status;

    private FindResponseDto member;
}
