package com.daol.logbuffer.image.application;

import com.daol.logbuffer.image.common.ImageType;
import com.daol.logbuffer.image.domain.ImageRepository;
import com.daol.logbuffer.image.domain.ProfileImage;
import com.daol.logbuffer.image.domain.ProfileImageRepository;
import com.daol.logbuffer.image.domain.UploaderId;
import org.springframework.stereotype.Service;

@Service
public class ProfileImageService extends ImageService<ProfileImage> {

    private final ProfileImageRepository imageRepository;

    public ProfileImageService(FileStorage fileStorage, ImagePathService imagePathService, ProfileImageRepository imageRepository) {
        super(fileStorage, imagePathService);
        this.imageRepository = imageRepository;
    }

    @Override
    ImageType getImageType() {
        return ImageType.PROFILE;
    }

    @Override
    ImageRepository<ProfileImage> getRepository() {
        return imageRepository;
    }

    @Override
    ProfileImage createImageEntity(UploaderId uploaderId) {
        return ProfileImage.create(uploaderId);
    }
}