package com.daol.logbuffer.post.application;

import com.daol.logbuffer.category.CategoryId;
import com.daol.logbuffer.hashtag.HashtagId;
import com.daol.logbuffer.post.command.Post;
import com.daol.logbuffer.post.command.PostAuthorId;
import com.daol.logbuffer.post.command.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreationService {

    private final PostRepository postRepository;

    public PostCreationResponse createPost(PostAuthorId authorId, PostCreationRequest postReq) {
        return PostCreationResponse.from(
            postRepository.save(
                Post.create(postReq.title(), postReq.content(), authorId,
                    new CategoryId(postReq.categoryId()),
                    postReq.hashtagIds().stream().map(HashtagId::new).toList(), postReq.state()))
        );
    }
}