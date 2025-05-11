package com.daol.logbuffer.post.application;

import com.daol.logbuffer.image.domain.postimage.PostImageRepository;
import com.daol.logbuffer.image.domain.postthumbnailimage.PostThumbnailImageRepository;
import com.daol.logbuffer.post.command.Post;
import com.daol.logbuffer.post.command.PostAuthorId;
import com.daol.logbuffer.post.command.PostId;
import com.daol.logbuffer.post.command.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDeletionService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final PostThumbnailImageRepository postThumbnailImageRepository;

    public void delete(PostAuthorId authorId, PostId postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.verifyAuthor(authorId);
        postRepository.delete(post);
    }
}
