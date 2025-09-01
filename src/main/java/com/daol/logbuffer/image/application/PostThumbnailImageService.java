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

    @Transactional(readOnly = true)
    public String findFileNameByPostId(PostId postId) {
        return imageRepository.findByPostId(postId).map(PostThumbnailImage::getFileName)
            .orElseThrow(() -> new EntityNotFoundException("해당 PostId에 대한 이미지 파일명을 찾을 수 없습니다."));
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

    @Transactional
    public ImageResponse createImage(UploaderId uploaderId, MultipartFile file) {
        PostThumbnailImage image = imageRepository.save(createImageEntity(uploaderId));
        String fileName = fileStorage.writeThumbnailFile(file, imagePathService.getImagePath(getImageType()));
        image.changeFileName(fileName);
        return new ImageResponse(imagePathService.getImageUrl(getImageType(), fileName));
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