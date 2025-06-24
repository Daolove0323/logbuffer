package com.daol.logbuffer.image.application;

import com.daol.logbuffer._common.config.ImageConfig;
import com.daol.logbuffer.image.common.ImageType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ImagePathService {

    private final ImageConfig imageConfig;

    private String getDirectory(ImageType type) {
        return switch (type) {
            case POST -> imageConfig.getPostImageDirectory();
            case POST_THUMBNAIL -> imageConfig.getPostThumbnailImageDirectory();
            case PROFILE -> imageConfig.getProfileImageDirectory();
        };
    }

    public String getImagePath(ImageType type) {
        return String.format("%s/%s", imageConfig.getImageFilePath(), getDirectory(type));
    }

    public String getImageUrl(ImageType type, String fileName) {
        return String.format("%s/%s/%s", imageConfig.getImageApiUrl(), getDirectory(type), fileName);
    }
}