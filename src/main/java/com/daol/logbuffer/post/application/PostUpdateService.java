package com.daol.logbuffer.post.application;

import com.daol.logbuffer.category.CategoryId;
import com.daol.logbuffer.hashtag.HashtagId;
import com.daol.logbuffer.post.command.Post;
import com.daol.logbuffer.post.command.PostAuthorId;
import com.daol.logbuffer.post.command.PostId;
import com.daol.logbuffer.post.command.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostUpdateService {

    private final PostRepository postRepository;

    public PostUpdateResponse updatePost(PostAuthorId authorId, PostId postId,
        PostUpdateRequest postReq) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.verifyAuthor(authorId);
        post.updateDetails(postReq.title(), postReq.content(), new CategoryId(postReq.categoryId()),
            postReq.hashtagIds().stream().map(HashtagId::new).toList(), postReq.state());
        return PostUpdateResponse.from(post);
    }
}