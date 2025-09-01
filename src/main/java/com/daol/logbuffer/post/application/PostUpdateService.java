package com.daol.logbuffer.post.application;

import com.daol.logbuffer._common.event.Events;
import com.daol.logbuffer._common.event.PostUpdatedEvent;
import com.daol.logbuffer._common.exception.EntityNotFoundException;
import com.daol.logbuffer._common.util.ContentParser;
import com.daol.logbuffer.post.command.Post;
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
    public PostUpdateResponse updatePost(PostUpdateCommand cmd
    ) {
        Post post = postRepository.findById(cmd.postId())
            .orElseThrow(() -> new EntityNotFoundException("ID에 해당하는 게시글을 찾을 수 없습니다."));
        post.verifyAuthor(cmd.authorId());
        post.updateDetails(cmd.title(), cmd.description(), cmd.content(), cmd.categoryId(), cmd.hashtagIds(), cmd.state());
        List<String> imageUrls = ContentParser.ParseImageUrls(cmd.content());
        Events.raise(new PostUpdatedEvent(cmd.authorId(), post.getId(), imageUrls, cmd.thumbnailImageUrl(), cmd.hashtagIds()));
        return PostUpdateResponse.from(post);
    }
}