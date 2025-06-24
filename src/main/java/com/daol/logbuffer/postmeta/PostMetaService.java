package com.daol.logbuffer.postmeta;

import com.daol.logbuffer._common.exception.EntityNotFoundException;
import com.daol.logbuffer.post.command.PostId;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostMetaService {

    private final PostMetaRepository postMetaRepository;

    @Transactional
    public void createPostMeta(PostId postId, List<String> hashtags) {
        postMetaRepository.save(PostMeta.create(hashtags));
    }
    
    @Transactional
    public void incrementCommentCount(PostId postId) {
        PostMeta postMeta = postMetaRepository.findById(postId)
            .orElseThrow(() -> new EntityNotFoundException("ID에 해당하는 게시글 메타정보를 찾을 수 없습니다."));
        postMeta.incrementCommentCount();
    }

    @Transactional
    public void decrementCommentCount(PostId postId) {
        PostMeta postMeta = postMetaRepository.findById(postId)
            .orElseThrow(() -> new EntityNotFoundException("ID에 해당하는 게시글 메타정보를 찾을 수 없습니다."));
        postMeta.incrementCommentCount();
    }
}