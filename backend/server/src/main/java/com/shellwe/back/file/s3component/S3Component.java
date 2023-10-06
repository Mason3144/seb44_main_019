package com.shellwe.back.file.s3component;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class S3Component {

    @Value("${cloud.s3.bucket}")
    private String bucket;

    @Value("${cloud.s3.folder.folderName1}")
    private String memberFolder;

    @Value("${cloud.s3.folder.folderName2}")
    private String shellFolder;
}
