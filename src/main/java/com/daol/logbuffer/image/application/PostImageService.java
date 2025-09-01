package com.daol.logbuffer.image.application;

import com.daol.logbuffer._common.exception.EntityNotFoundException;
import com.daol.logbuffer._common.exception.InvalidRequestException;
import com.daol.logbuffer.image.common.ImageType;
import com.daol.logbuffer.image.domain.ImageRepository;
import com.daol.logbuffer.image.domain.PostImage;
import com.daol.logbuffer.image.domain.PostImageRepository;
import com.daol.logbuffer.image.domain.UploaderId;
import com.daol.logbuffer.post.command.PostId;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostImageService extends ImageService<PostImage> {

    private final PostImageRepository imageRepository;

    public PostImageService(FileStorage fileStorage, ImagePathService imagePathService, PostImageRepository imageRepository) {
        super(fileStorage, imagePathService);
        this.imageRepository = imageRepository;
    }

    @Override
    @Transactional
    public void setImageReference(String fileName, Object id) {
        if (!(id instanceof PostId postId)) {
            throw new InvalidRequestException("ID는 PostId 타입이어야 합니다.");
        }
        PostImage image = imageRepository.findByFileName(fileName)
            .orElseThrow(() -> new EntityNotFoundException("파일명에 해당하는 이미지를 찾을 수 없습니다."));
        image.setPostReference(postId);
    }

    @Transactional
    public void resetImageReference(PostId postId, List<String> imageUrls) {
        unlinkImagesFromPost(postId);
        linkImagesToPost(postId, imageUrls);
    }

    @Transactional
    public void unlinkImagesFromPost(PostId postId) {
        List<PostImage> images = imageRepository.findAllByPostId(postId);
        for (PostImage image : images) {
            image.clearPostReference();
        }
    }

    @Transactional
    public void linkImagesToPost(PostId postId, List<String> imageUrls) {
        for (String url : imageUrls) {
            String[] paths = url.split("/");
            String fileName = paths[paths.length - 1];
            setImageReference(fileName, postId);
        }
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