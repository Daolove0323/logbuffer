package com.daol.logbuffer.post.application;

import com.daol.logbuffer._common.event.Events;
import com.daol.logbuffer._common.event.PostDeletedEvent;
import com.daol.logbuffer._common.exception.EntityNotFoundException;
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

    // Todo: 익셉션 처리
    // Todo: 포스트 삭제 이벤트를 발행하여 PostMeta 테이블에 행 삭제/모든 댓글 및 좋아요/이미지 비활성화
    public void delete(PostAuthorId authorId, PostId postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new EntityNotFoundException("ID에 해당하는 포스트를 찾을 수 없습니다."));
        post.verifyAuthor(authorId);
        post.delete();
        Events.raise(new PostDeletedEvent(postId, post.getCategoryId()));
    }
}