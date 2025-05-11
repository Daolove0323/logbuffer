package com.daol.logbuffer.post.application;

import com.daol.logbuffer.category.CategoryId;
import com.daol.logbuffer.hashtag.HashtagId;
import com.daol.logbuffer.post.command.image.PostImage;
import com.daol.logbuffer.post.command.image.PostImageRepository;
import com.daol.logbuffer.post.command.image.PostThumbnailImage;
import com.daol.logbuffer.post.command.image.PostThumbnailImageRepository;
import com.daol.logbuffer.post.command.post.Post;
import com.daol.logbuffer.post.command.post.PostAuthorId;
import com.daol.logbuffer.post.command.post.PostRepository;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreationService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final PostThumbnailImageRepository postThumbnailImageRepository;

    public PostCreationResponse createPost(PostAuthorId authorId, PostCreationRequest postReq) {
        PostThumbnailImage postThumbnailImage =
            postThumbnailImageRepository.findByUrl(postReq.thumbnailUrl()).orElse(null);
        return PostCreationResponse.from(
            postRepository.save(
                Post.create(postReq.title(), postReq.content(), authorId, new CategoryId(postReq.categoryId()),
                    postReq.hashtagIds().stream().map(HashtagId::new).toList(), postReq.state(), postThumbnailImage))
        );
    }
}