package com.daol.logbuffer.post.application;

import com.daol.logbuffer._common.event.Events;
import com.daol.logbuffer._common.event.PostUpdatedEvent;
import com.daol.logbuffer._common.exception.EntityNotFoundException;
import com.daol.logbuffer._common.util.UrlUtil;
import com.daol.logbuffer.category.CategoryId;
import com.daol.logbuffer.hashtag.HashtagId;
import com.daol.logbuffer.post.command.Post;
import com.daol.logbuffer.post.command.PostAuthorId;
import com.daol.logbuffer.post.command.PostId;
import com.daol.logbuffer.post.command.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostUpdateService {

    private final PostRepository postRepository;

    // Todo: 익셉션 처리
    // Todo: 포스트 수정 이벤트를 발행하여 해시태그 변경 고려
    @Transactional
    public PostUpdateResponse updatePost(PostAuthorId authorId, PostId postId, PostUpdateRequest postReq) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new EntityNotFoundException("ID에 해당하는 게시글을 찾을 수 없습니다."));
        post.verifyAuthor(authorId);
        List<HashtagId> hashtagIds = postReq.hashtagIds().stream().map(HashtagId::new).toList();
        post.updateDetails(postReq.title(), postReq.description(), postReq.content(), new CategoryId(postReq.categoryId()),
            hashtagIds, postReq.state());
        List<String> imageUrls = UrlUtil.extractUrls(postReq.content());
        Events.raise(new PostUpdatedEvent(authorId, post.getId(), imageUrls, postReq.thumbnailImageUrl(), hashtagIds));
        return PostUpdateResponse.from(post);
    }
}