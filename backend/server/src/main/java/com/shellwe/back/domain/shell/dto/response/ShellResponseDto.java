package com.shellwe.back.domain.shell.dto.response;

import com.shellwe.back.domain.member.dto.response.MemberDtoExceptIsMe;
import com.shellwe.back.entity.type.ShellType;
import com.shellwe.back.entity.type.Status;
import com.shellwe.back.entity.ShellCategory;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ShellResponseDto {

    private Long id;

    private ShellType type;

    private Status status;

    private String title;

    private String createdAt;

    private ShellCategory category;

    private String picture;

    private MemberDtoExceptIsMe member;
}
