package com.daol.logbuffer.comment.post;

import com.daol.logbuffer._common.exception.EntityNotFoundException;
import com.daol.logbuffer.post.command.PostId;
import com.daol.logbuffer.post.command.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostChecker {

    private final PostRepository postRepository;

    public void verifyPostExists(PostId postId) {
        if (!postRepository.existsById(postId)) {
            throw new EntityNotFoundException("ID에 해당하는 게시글을 찾을 수 없습니다.");
        }
    }
}