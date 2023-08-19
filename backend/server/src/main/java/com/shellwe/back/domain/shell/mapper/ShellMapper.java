package com.shellwe.back.domain.shell.mapper;

import com.shellwe.back.entity.type.Category;
import com.shellwe.back.domain.member.dto.response.FindResponseDto;
import com.shellwe.back.entity.Member;
import com.shellwe.back.domain.picture.dto.PictureResponseDto;
import com.shellwe.back.entity.Picture;
import com.shellwe.back.domain.shell.dto.request.RegisterRequestDto;
import com.shellwe.back.domain.shell.dto.request.UpdateRequestDto;
import com.shellwe.back.domain.shell.dto.response.*;
import com.shellwe.back.entity.Shell;
import com.shellwe.back.domain.tag.dto.TagResponseDto;
import com.shellwe.back.entity.Tag;
import com.shellwe.back.domain.trade.dto.response.UpdateTradeStatusResponseDto;
import com.shellwe.back.entity.ShellCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ShellMapper {

    List<ShellResponseDto> shellsToInquiryResponseDto(List<Shell> shells);

    List<ShellResponseDto> shellsToSearchResponseDto(List<Shell> shells);

    @Mapping(target="member", source="shell.member", qualifiedByName="mapMemberToDto")
    @Mapping(target="pictures", source="shell.pictureUrls", qualifiedByName="mapPicturesToDto")
    @Mapping(target="tags", source="shell.tags", qualifiedByName="mapTagsToDto")
    @Mapping(target="category", source="shell.category", qualifiedByName="mapCategoryToDto")
    @Mapping(target="type", source="shell.shellType")
    FindDetailsResponseDto shellToFindDetailsResponseDto(Shell shell);

    @Mapping(target="member", source="shell.member", qualifiedByName="mapMemberToDto")
    @Mapping(target="pictures", source="shell.pictureUrls", qualifiedByName="mapPicturesToDto")
    @Mapping(target="tags", source="shell.tags", qualifiedByName="mapTagsToDto")
    @Mapping(target="category", source="shell.category", qualifiedByName="mapCategoryToDto")
    @Mapping(target="type", source="shell.shellType")
    UpdateTradeStatusResponseDto shellToUpdateTradeStatusResponseDto(Shell shell);

    @Mapping(target = "shellType", source = "registerRequestDto.type")
    @Mapping(target = "title", source = "registerRequestDto.title")
    @Mapping(target = "body", source = "registerRequestDto.body")
    @Mapping(target = "tags", source = "registerRequestDto.tags", qualifiedByName = "mapTagsToStringDto")
    @Mapping(target = "category.shellCategory", source = "registerRequestDto.category", ignore = true)
    Shell registerRequestDtoToShell(RegisterRequestDto registerRequestDto);

    @Mapping(target = "shellType", source = "updateRequestDto.type")
    @Mapping(target = "title", source = "updateRequestDto.title")
    @Mapping(target = "body", source = "updateRequestDto.body")
    @Mapping(target = "category", source = "updateRequestDto.category", qualifiedByName = "mapCategoryFromDto")
    @Mapping(target = "tags", source = "updateRequestDto.tags", qualifiedByName = "mapTagsToStringDto")
    Shell updateRequestDtoToShell(UpdateRequestDto updateRequestDto);

    @Mapping(target="type", source="shell.shellType")
    @Mapping(target="createdAt", source="shell.createdAt")
    @Mapping(target="modifiedAt", source="shell.modifiedAt")
    @Mapping(target="category", source="shell.category.shellCategory")
    @Mapping(target="status", source="shell.status")
    @Mapping(target="pictures", source="shell.pictureUrls", qualifiedByName="mapPicturesToDto")
    @Mapping(target="tags", source="shell.tags", qualifiedByName="mapTagsToDto")
    @Mapping(target="member", source="shell.member", qualifiedByName="mapMemberToDto")
    UpdateResponseDto shellToUpdateResponseDto(Shell shell);

    @Mapping(target="type", source="shell.shellType")
    @Mapping(target="createdAt", source="shell.createdAt")
    @Mapping(target="category", source="shell.category.shellCategory")
    @Mapping(target="status", source="shell.status")
    @Mapping(target="pictures", source="shell.pictureUrls", qualifiedByName="mapPicturesToDto")
    @Mapping(target="tags", source="shell.tags", qualifiedByName="mapTagsToDto")
    @Mapping(target="member", source="shell.member", qualifiedByName="mapMemberToDto")
    RegisterResponseDto shellToRegisterResponseDto(Shell shell);

    @Mapping(target="picture", expression="java(mapFirstPictureToDto(shell.getPictureUrls()))")
    @Mapping(target="type", source="shell.shellType")
    @Mapping(target="category", source="shell.category.shellCategory")
    ShellResponseDto shellToShellResponseDto(Shell shell);

    default String mapFirstPictureToDto(List<Picture> pictures) {
        if (pictures != null && !pictures.isEmpty()) {
            return pictures.get(0).getUrl();
        }
        return null;
    }

    @Named("mapCategoryToShellCategory")
    default ShellCategory mapCategoryToShellCategory(Category category) {
        if (category == null) {
            return null;
        }
        return category.getShellCategory();
    }

    @Named("mapCategoryToString")
    default String mapCategoryToString(Category category) {
        if (category == null || category.getShellCategory() == null) {
            return null;
        }
        return category.getShellCategory().lowerAtResponse();
    }

    @Named("mapStringToCategory")
    default Category mapStringToCategory(String category) {
        if (category == null) {
            return null;
        }
        Category newCategory = new Category();
        newCategory.setShellCategory(ShellCategory.upperAtRequest(category));
        return newCategory;
    }

    @Named("mapCategoryFromDto")
    default Category mapCategoryFromDto(ShellCategory shellCategory) {
        Category category = new Category();
        category.setShellCategory(shellCategory);
        return category;
    }

    @Named("mapMemberToDto")
    default FindResponseDto mapMemberToDto(Member member) {
        FindResponseDto responseDto = new FindResponseDto();
        responseDto.setId(member.getId());
        responseDto.setDisplayName(member.getDisplayName());
        responseDto.setProfileUrl(member.getProfileUrl());
        return responseDto;
    }

    @Named("mapPicturesToDto")
    default List<PictureResponseDto> mapPicturesToDto(List<Picture> pictures) {
        return pictures.stream()
                .map(picture -> {
                    PictureResponseDto dto = new PictureResponseDto();
                    dto.setOrder(picture.getOrder());
                    dto.setUrl(picture.getUrl());
                    return dto;
                })
                .collect(Collectors.toList());
    }

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

    @Named("mapCategoryToDto")
    default ShellCategory mapCategoryToDto(Category category) {
        return category.getShellCategory();
    }

    @Named("mapTagsToStringDto")
    default List<Tag> mapTagsToStringDto(List<String> tagNames) {
        if (tagNames == null || tagNames.isEmpty()) {
            return null;
        }
        return tagNames.stream()
                .filter(tagName -> tagName != null && !tagName.trim().isEmpty())
                .map(tagName -> {
                    Tag tag = new Tag();
                    tag.setTagName(tagName);
                    return tag;
                })
                .collect(Collectors.toList());
    }
}
