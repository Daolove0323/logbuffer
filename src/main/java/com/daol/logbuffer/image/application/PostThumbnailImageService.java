package com.daol.logbuffer.image.application;

import com.daol.logbuffer.image.common.ImageType;
import com.daol.logbuffer.image.domain.ImageRepository;
import com.daol.logbuffer.image.domain.PostThumbnailImage;
import com.daol.logbuffer.image.domain.PostThumbnailImageRepository;
import com.daol.logbuffer.image.domain.UploaderId;
import org.springframework.stereotype.Service;

@Service
public class PostThumbnailImageService extends ImageService<PostThumbnailImage> {

    private final PostThumbnailImageRepository imageRepository;

    public PostThumbnailImageService(FileStorage fileStorage, ImagePathService imagePathService, PostThumbnailImageRepository imageRepository) {
        super(fileStorage, imagePathService);
        this.imageRepository = imageRepository;
    }

    @Override
    ImageType getImageType() {
        return ImageType.POST_THUMBNAIL;
    }

    @Override
    ImageRepository<PostThumbnailImage> getRepository() {
        return imageRepository;
    }

    @Override
    PostThumbnailImage createImageEntity(UploaderId uploaderId) {
        return PostThumbnailImage.create(uploaderId);
    }
}