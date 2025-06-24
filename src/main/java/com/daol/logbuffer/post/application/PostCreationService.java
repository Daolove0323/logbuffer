package com.daol.logbuffer.post.application;

import com.daol.logbuffer._common.event.Events;
import com.daol.logbuffer._common.event.PostCreatedEvent;
import com.daol.logbuffer.category.CategoryId;
import com.daol.logbuffer.hashtag.HashtagId;
import com.daol.logbuffer.post.command.Post;
import com.daol.logbuffer.post.command.PostAuthorId;
import com.daol.logbuffer.post.command.PostRepository;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreationService {

    private final PostRepository postRepository;

    public PostCreationResponse createPost(PostAuthorId authorId, PostCreationRequest postReq) {
        List<HashtagId> hashtagIds = postReq.hashtagIds().stream().map(HashtagId::new).toList();
        Post post = postRepository.save(Post.create(
            postReq.title(), postReq.content(), authorId, new CategoryId(postReq.categoryId()), hashtagIds, postReq.state()));
        Events.raise(new PostCreatedEvent(post.getId(), hashtagIds));
        return PostCreationResponse.from(post);
    }
}