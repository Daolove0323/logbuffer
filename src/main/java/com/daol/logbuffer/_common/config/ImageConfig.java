package com.daol.logbuffer._common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ImageConfig {

    @Value("${image.api-url}")
    private String imageApiUrl;

    @Value("${image.storage-path}")
    private String imageFilePath;

    @Value("${image.post-directory}")
    private String postImageDirectory;

    @Value("${image.post-thumbnail-directory}")
    private String postThumbnailImageDirectory;

    @Value("${image.profile-directory}")
    private String profileImageDirectory;
}