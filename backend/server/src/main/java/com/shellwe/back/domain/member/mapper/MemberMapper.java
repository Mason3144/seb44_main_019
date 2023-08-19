package com.shellwe.back.domain.member.mapper;

import com.shellwe.back.domain.member.dto.request.SignUpRequestDto;
import com.shellwe.back.domain.member.dto.request.UpdateRequestDto;
import com.shellwe.back.domain.member.dto.response.FindResponseDto;
import com.shellwe.back.domain.member.dto.response.ShellForMyShellListDtoTags;
import com.shellwe.back.entity.Member;
import com.shellwe.back.entity.Picture;
import com.shellwe.back.domain.shell.dto.response.ShellResponseDto;
import com.shellwe.back.entity.Shell;
import com.shellwe.back.domain.tag.dto.TagResponseDto;
import com.shellwe.back.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member signUpRequestDtoToMember(SignUpRequestDto signUpRequestDto);

    Member updateRequestDtoToMember(UpdateRequestDto updateRequestDto);

    FindResponseDto memberToFindResponseDto(Member member);

    List<ShellResponseDto> shellListToGetMyShellListDto(List<Shell> activeShellList);

    List<ShellForMyShellListDtoTags> shellListToGetMyShellListDtoTags(List<Shell> activeShellList);

    @Mapping(target="picture", expression="java(mapFirstPictureToDto(shell.getPictureUrls()))")
    @Mapping(target="type", source="shell.shellType")
    @Mapping(target="category", source="shell.category.shellCategory")
    ShellResponseDto shellToShellResponseDto(Shell shell);

    @Mapping(target="picture", expression="java(mapFirstPictureToDto(shell.getPictureUrls()))")
    @Mapping(target="type", source="shell.shellType")
    @Mapping(target="category", source="shell.category.shellCategory")
    @Mapping(target="tags", source="shell.tags", qualifiedByName="mapTagsToDto")
    ShellForMyShellListDtoTags shellToShellForMyShellListDtoTags(Shell shell);

    @Named("mapTagsToDto")
    default List<TagResponseDto> mapTagsToDto(List<Tag> tags) {
        if (tags == null || tags.isEmpty()) {
            return null;
        }
        return tags.stream()
                .map(tag -> {
                    TagResponseDto dto = new TagResponseDto();
                    dto.setTagName(tag.getTagName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    default String mapFirstPictureToDto(List<Picture> pictures) {
        if (pictures != null && !pictures.isEmpty()) {
            return pictures.get(0).getUrl();
        }
        return null;
    }
}
