package com.shellwe.back.domain.member.dto.response;

import com.shellwe.back.domain.tag.dto.TagResponseDto;
import com.shellwe.back.entity.type.ShellType;
import com.shellwe.back.entity.type.Status;
import com.shellwe.back.entity.ShellCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShellForMyShellListDtoTags {

    private Long id;

    private ShellType type;

    private Status status;

    private String title;

    private String createdAt;

    private ShellCategory category;

    private List<TagResponseDto> tags;

    private String picture;

    private MemberDtoExceptIsMe member;
}
