package com.daol.logbuffer.image.application;

import com.daol.logbuffer.image.domain.UploaderId;
import com.daol.logbuffer.image.domain.profileimage.ProfileImage;
import com.daol.logbuffer.image.domain.profileimage.ProfileImageId;
import com.daol.logbuffer.image.domain.profileimage.ProfileImageRepository;
import com.daol.logbuffer.image.ui.ImageType;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileImageService implements ImageService {

    private final ProfileImageRepository imageRepository;

    @Override
    public ImageType getImageType() {
        return ImageType.PROFILE;
    }

    @Override
    public UUID createImage(UploaderId uploaderId) {
        ProfileImage image = ProfileImage.create(uploaderId);
        imageRepository.save(image);
        return image.getId().getValue();
    }

    @Override
    public void deleteImage(ProfileImageId imageId, UploaderId uploaderId) {
        imageRepository.findById(imageId).orElseThrow();
        imageRepository.deleteById(imageId);
    }
}
