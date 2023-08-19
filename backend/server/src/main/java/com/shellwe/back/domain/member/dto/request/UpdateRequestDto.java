package com.shellwe.back.domain.member.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateRequestDto {

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$",
            message = "Must contain at least 8 characters, including letters, numbers.")
    private String password;

    @Size(max = 8, message = "The displayname size must be greater than 0 and less than 8.")
    private String displayName;

    @Size(max = 150, message = "The introduction size must be  less than 150.")
    private String introduction;

    private MultipartFile pictureFile;
}
