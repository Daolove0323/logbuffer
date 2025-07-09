package com.daol.logbuffer.image.application;

import com.daol.logbuffer._common.exception.EntityNotFoundException;
import com.daol.logbuffer._common.exception.InvalidRequestException;
import com.daol.logbuffer.image.common.ImageType;
import com.daol.logbuffer.image.domain.ImageRepository;
import com.daol.logbuffer.image.domain.PostThumbnailImage;
import com.daol.logbuffer.image.domain.PostThumbnailImageRepository;
import com.daol.logbuffer.image.domain.UploaderId;
import com.daol.logbuffer.post.command.PostId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostThumbnailImageService extends ImageService<PostThumbnailImage> {

    private final PostThumbnailImageRepository imageRepository;

    public PostThumbnailImageService(FileStorage fileStorage, ImagePathService imagePathService, PostThumbnailImageRepository imageRepository) {
        super(fileStorage, imagePathService);
        this.imageRepository = imageRepository;
    }

    @Override
    @Transactional
    public void setImageReference(String fileName, Object id) {
        if (!(id instanceof PostId postId)) {
            throw new InvalidRequestException("ID는 PostId 타입이어야 합니다.");
        }
        PostThumbnailImage image = imageRepository.findByFileName(fileName)
            .orElseThrow(() -> new EntityNotFoundException("파일명에 해당하는 이미지를 찾을 수 없습니다."));
        image.setPostReference(postId);
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