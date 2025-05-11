package com.daol.logbuffer.image.application;

import com.daol.logbuffer.image.domain.UploaderId;
import com.daol.logbuffer.image.domain.postthumbnailimage.PostThumbnailImage;
import com.daol.logbuffer.image.domain.postthumbnailimage.PostThumbnailImageId;
import com.daol.logbuffer.image.domain.postthumbnailimage.PostThumbnailImageRepository;
import com.daol.logbuffer.image.ui.ImageType;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostThumbnailImageService implements ImageService {

    private final PostThumbnailImageRepository imageRepository;

    @Override
    public ImageType getImageType() {
        return ImageType.POST_THUMBNAIL;
    }

    @Override
    public UUID createImage(UploaderId uploaderId) {
        PostThumbnailImage image = PostThumbnailImage.create(uploaderId);
        imageRepository.save(image);
        return image.getId().getValue();
    }

    @Override
    public void deleteImage(UUID imageId, UploaderId uploaderId) {
        PostThumbnailImageId id = new PostThumbnailImageId(imageId);
        imageRepository.findById(id).orElseThrow();
        imageRepository.deleteById(id);
    }
}