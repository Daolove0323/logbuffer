package com.daol.logbuffer.postlike.query;

import com.daol.logbuffer.post.command.post.PostId;
import com.daol.logbuffer.postlike.command.PostLikeRepository;
import com.daol.logbuffer.postlike.command.PostLikerId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikeQueryService {

    private final PostLikeRepository likeRepository;

    @Transactional(readOnly = true)
    public Boolean getLike(PostId postId, PostLikerId likerId) {
        return likeRepository.findByPostIdAndLikerId(postId, likerId).isPresent();
    }
}