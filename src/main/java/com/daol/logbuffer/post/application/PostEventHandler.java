package com.daol.logbuffer.post.application;

import com.daol.logbuffer._common.event.PostCreatedEvent;
import com.daol.logbuffer._common.event.PostDeletedEvent;
import com.daol.logbuffer.category.CategoryService;
import com.daol.logbuffer.hashtag.HashtagService;
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

    @Async
    @EventListener
    public void handlePostCreatedEvent(PostCreatedEvent event) {
        List<String> hashtags = hashtagService.getHashtagNames(event.getHashtagIds());
        postMetaService.createPostMeta(event.getPostId(), hashtags);
    }

    @Async
    @EventListener
    public void handlePostDeletedEvent(PostDeletedEvent event) {
        categoryService.deleteCategoryIfNotUsed(event.getCategoryId());
    }
}