package com.daol.logbuffer.postlike.application;

import com.daol.logbuffer.post.command.PostId;
import com.daol.logbuffer.postlike.command.PostLike;
import com.daol.logbuffer.postlike.command.PostLikeRepository;
import com.daol.logbuffer.postlike.command.PostLikerId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikeToggleService {

    private final PostLikeRepository likeRepository;

    @Transactional
    public void toggleLike(PostId postId, PostLikerId likerId) {
        likeRepository.findByPostIdAndLikerId(postId, likerId)
            .ifPresentOrElse(
                (existing) -> likeRepository.delete(existing),
                () -> likeRepository.save(PostLike.create(postId, likerId))
            );
    }
}