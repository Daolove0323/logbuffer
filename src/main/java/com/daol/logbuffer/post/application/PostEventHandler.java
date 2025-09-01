package com.daol.logbuffer.post.application;

import com.daol.logbuffer._common.event.PostCreatedEvent;
import com.daol.logbuffer._common.event.PostDeletedEvent;
import com.daol.logbuffer._common.event.PostUpdatedEvent;
import com.daol.logbuffer._common.event.PostViewedEvent;
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

    @EventListener
    public void handlePostCreatedEvent(PostCreatedEvent event) {
        postImageService.linkImagesToPost(event.getPostId(), event.getPostImageUrls());
        if (event.getThumbnailImageUrl() != null && !event.getThumbnailImageUrl().isEmpty()) {
            postThumbnailImageService.linkImageToPost(event.getThumbnailImageUrl(), event.getPostId());
        }
        List<String> hashtags = hashtagService.getHashtagNames(event.getHashtagIds());
        postMetaService.createPostMeta(event.getPostId(), hashtags);
    }

    @EventListener
    public void handlePostUpdatedEvent(PostUpdatedEvent event) {
        postImageService.resetImageReference(event.getPostId(), event.getPostImageUrls());
        if (event.getThumbnailImageUrl() != null && !event.getThumbnailImageUrl().isEmpty()) {
            postThumbnailImageService.resetImageReference(event.getThumbnailImageUrl(), event.getPostId());
        }
        List<String> hashtags = hashtagService.getHashtagNames(event.getHashtagIds());
        postMetaService.changeHashtags(event.getPostId(), hashtags);
    }

    @Async
    @EventListener
    public void handlePostDeletedEvent(PostDeletedEvent event) {
        categoryService.deleteCategoryIfNotUsed(event.getCategoryId());
    }

    @Async
    @EventListener
    public void handlePostViewedEvent(PostViewedEvent event) {
        postMetaService.incrementViewCount(event.getPostId());
    }
}