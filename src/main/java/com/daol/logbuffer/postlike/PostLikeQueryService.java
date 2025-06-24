package com.daol.logbuffer.postlike;

import com.daol.logbuffer.post.command.PostId;
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