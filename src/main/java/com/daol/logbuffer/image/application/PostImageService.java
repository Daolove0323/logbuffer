package com.daol.logbuffer.image.application;

import com.daol.logbuffer.image.domain.ImageId;
import com.daol.logbuffer.image.domain.UploaderId;
import com.daol.logbuffer.image.domain.postimage.PostImage;
import com.daol.logbuffer.image.domain.postimage.PostImageId;
import com.daol.logbuffer.image.domain.postimage.PostImageRepository;
import com.daol.logbuffer.image.ui.ImageType;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostImageService implements ImageService {

    private final PostImageRepository imageRepository;

    @Override
    public ImageType getImageType() {
        return ImageType.POST;
    }

    @Override
    public PostImageId createImage(UploaderId uploaderId) {
        PostImage image = PostImage.create(uploaderId);
        imageRepository.save(image);
        return image.getId();
    }

    @Override
    public void deleteImage(ImageId imageId, UploaderId uploaderId) {
        PostImageId id = new PostImageId(imageId);
        imageRepository.findById(id).orElseThrow();
        imageRepository.deleteById(id);
    }
}