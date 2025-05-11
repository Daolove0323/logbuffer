package com.daol.logbuffer.post.application;

import com.daol.logbuffer.post.command.image.PostImageRepository;
import com.daol.logbuffer.post.command.image.PostThumbnailImageRepository;
import com.daol.logbuffer.post.command.post.Post;
import com.daol.logbuffer.post.command.post.PostAuthorId;
import com.daol.logbuffer.post.command.post.PostId;
import com.daol.logbuffer.post.command.post.PostRepository;
import java.util.UUID;
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
