package com.daol.logbuffer.post.application;

import com.daol.logbuffer._common.event.PostCreatedEvent;
import com.daol.logbuffer._common.event.PostDeletedEvent;
import com.daol.logbuffer.category.CategoryService;
import com.daol.logbuffer.hashtag.HashtagService;
import com.daol.logbuffer.image.application.PostImageService;
import com.daol.logbuffer.image.application.PostThumbnailImageService;
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