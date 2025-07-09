package com.daol.logbuffer.post.application;

import com.daol.logbuffer._common.event.Events;
import com.daol.logbuffer._common.event.PostCreatedEvent;
import com.daol.logbuffer._common.util.UrlUtil;
import com.daol.logbuffer.category.CategoryId;
import com.daol.logbuffer.hashtag.HashtagId;
import com.daol.logbuffer.post.command.Post;
import com.daol.logbuffer.post.command.PostAuthorId;
import com.daol.logbuffer.post.command.PostRepository;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreationService {

    private final PostRepository postRepository;

    // Todo: PostImageUrls 파싱하여 이벤트 파리미터로 전달
    @Transactional
    public PostCreationResponse createPost(PostAuthorId authorId, PostCreationRequest postReq) {
        List<HashtagId> hashtagIds = postReq.hashtagIds().stream().map(HashtagId::new).toList();
        Post post = postRepository.save(Post.create(
            postReq.title(), postReq.description(), postReq.content(), authorId, new CategoryId(postReq.categoryId()), hashtagIds, postReq.state()));
        List<String> imageUrls = UrlUtil.extractUrls(postReq.content());
        Events.raise(new PostCreatedEvent(post.getId(), imageUrls, postReq.thumbnailImageUrl(), hashtagIds));
        return PostCreationResponse.from(post);
    }
}