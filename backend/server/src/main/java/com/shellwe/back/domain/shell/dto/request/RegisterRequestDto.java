package com.shellwe.back.domain.shell.dto.request;

import com.shellwe.back.entity.type.ShellType;
import com.shellwe.back.entity.ShellCategory;
import com.shellwe.back.utils.customannotation.EachSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class RegisterRequestDto {

    @NotNull (message = "type required")
    private ShellType type;

    @NotBlank(message = "title required")
    @Size(max = 20, message = "title within 20 characters")
    private String title;

    @NotBlank(message = "body required")
    @Size(max = 1500, message = "body within 1500 characters")
    private String body;

    @NotNull(message = "category required")
    private ShellCategory category;

    @EachSize(max = 20)
    private List<String> tags;

    @Size(min = 1, max = 4, message = "number of image files between 1 and 4")
    private List<MultipartFile> picturesFile;
}
