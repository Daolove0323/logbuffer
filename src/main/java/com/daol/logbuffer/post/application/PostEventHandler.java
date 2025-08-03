package com.daol.logbuffer.post.application;

import com.daol.logbuffer._common.event.PostCreatedEvent;
import com.daol.logbuffer._common.event.PostDeletedEvent;
import com.daol.logbuffer._common.event.PostUpdatedEvent;
import com.daol.logbuffer.category.CategoryService;
import com.daol.logbuffer.hashtag.HashtagService;
import com.daol.logbuffer.image.application.PostImageService;
import com.daol.logbuffer.image.application.PostThumbnailImageService;
import com.daol.logbuffer.image.domain.UploaderId;
import com.daol.logbuffer.postmeta.PostMetaService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEventHandler {

    private final PostMetaService postMetaService;
    private final HashtagService hashtagService;
    private final CategoryService categoryService;
    private final PostImageService postImageService;
    private final PostThumbnailImageService postThumbnailImageService;

    // Todo: 게시글 생성 이벤트 핸들링 리팩토링
    @EventListener
    public void handlePostCreatedEvent(PostCreatedEvent event) {
        for (String imageUrl : event.getPostImageUrls()) {
            String imageName = getImageName(imageUrl);
            postImageService.setImageReference(imageName, event.getPostId());
        }
        if (event.getThumbnailImageUrl() != null && !event.getThumbnailImageUrl().isEmpty()) {
            postThumbnailImageService.setImageReference(getImageName(event.getThumbnailImageUrl()), event.getPostId());
        }
        List<String> hashtags = hashtagService.getHashtagNames(event.getHashtagIds());
        postMetaService.createPostMeta(event.getPostId(), hashtags);
    }

    // Todo: 이미지 참조가 끊어지면, 이미지 파일 삭제 및 해당 로우 삭제
    @EventListener
    public void handlerPostUpdatedEvent(PostUpdatedEvent event) {
        for (String imageUrl : event.getPostImageUrls()) {
            String imageName = getImageName(imageUrl);
            // 기존 이미지 파일 삭제 비동기처리?
            postImageService.setImageReference(imageName, event.getPostId());
        }
        if (event.getThumbnailImageUrl() != null && !event.getThumbnailImageUrl().isEmpty()) {
            String fileName = postThumbnailImageService.findFileNameByPostId(event.getPostId());
            postThumbnailImageService.deleteImage(fileName, UploaderId.from(event.getAuthorId()));
            postThumbnailImageService.setImageReference(getImageName(event.getThumbnailImageUrl()), event.getPostId());
        }
        List<String> hashtags = hashtagService.getHashtagNames(event.getHashtagIds());
        // PostMeta 수정 메서드 추가
    }

    @Async
    @EventListener
    public void handlePostDeletedEvent(PostDeletedEvent event) {
        categoryService.deleteCategoryIfNotUsed(event.getCategoryId());
    }

    private String getImageName(String imageUrl) {
        String[] arr = imageUrl.split("/");
        return arr[arr.length - 1];
    }
}