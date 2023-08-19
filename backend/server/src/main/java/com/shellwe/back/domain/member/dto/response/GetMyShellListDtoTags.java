package com.shellwe.back.domain.member.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetMyShellListDtoTags {

    List<ShellForMyShellListDtoTags> shells;
}
