package com.daol.logbuffer.image.application;

import com.daol.logbuffer.image.common.ImageType;
import com.daol.logbuffer.image.domain.ImageRepository;
import com.daol.logbuffer.image.domain.PostImage;
import com.daol.logbuffer.image.domain.PostImageRepository;
import com.daol.logbuffer.image.domain.UploaderId;
import org.springframework.stereotype.Service;

@Service
public class PostImageService extends ImageService<PostImage> {

    private final PostImageRepository imageRepository;

    public PostImageService(FileStorage fileStorage, ImagePathService imagePathService, PostImageRepository imageRepository) {
        super(fileStorage, imagePathService);
        this.imageRepository = imageRepository;
    }

    @Override
    ImageType getImageType() {
        return ImageType.POST;
    }

    @Override
    ImageRepository<PostImage> getRepository() {
        return imageRepository;
    }

    @Override
    PostImage createImageEntity(UploaderId uploaderId) {
        return PostImage.create(uploaderId);
    }
}