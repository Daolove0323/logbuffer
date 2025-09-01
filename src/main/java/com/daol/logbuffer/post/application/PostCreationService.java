package com.daol.logbuffer.post.application;

import com.daol.logbuffer._common.event.Events;
import com.daol.logbuffer._common.event.PostCreatedEvent;
import com.daol.logbuffer._common.util.ContentParser;
import com.daol.logbuffer.post.command.Post;
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

    @Transactional
    public PostCreationResponse createPost(PostCreationCommand cmd) {
        Post post = postRepository.save(Post.create(
            cmd.title(), cmd.description(), cmd.content(), cmd.authorId(), cmd.categoryId(), cmd.hashtagIds(), cmd.state()));
        List<String> imageUrls = ContentParser.ParseImageUrls(cmd.content());
        Events.raise(new PostCreatedEvent(post.getId(), imageUrls, cmd.thumbnailImageUrl(), cmd.hashtagIds()));
        return PostCreationResponse.from(post);
    }
}