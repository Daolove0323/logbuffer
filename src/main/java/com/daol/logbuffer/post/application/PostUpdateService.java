package com.daol.logbuffer.post.application;

import com.daol.logbuffer.category.CategoryId;
import com.daol.logbuffer.hashtag.HashtagId;
import com.daol.logbuffer.post.command.image.PostImage;
import com.daol.logbuffer.post.command.image.PostImageRepository;
import com.daol.logbuffer.post.command.image.PostThumbnailImage;
import com.daol.logbuffer.post.command.image.PostThumbnailImageRepository;
import com.daol.logbuffer.post.command.post.Post;
import com.daol.logbuffer.post.command.post.PostAuthorId;
import com.daol.logbuffer.post.command.post.PostId;
import com.daol.logbuffer.post.command.post.PostRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostUpdateService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final PostThumbnailImageRepository postThumbnailImageRepository;

    public PostUpdateResponse updatePost(PostAuthorId authorId, PostId postId, PostUpdateRequest postReq) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.verifyAuthor(authorId);
        List<PostImage> postImages = postReq.postImageUrls().stream()
            .map(url -> postImageRepository.findByUrl(url).orElse(null))
            .toList();
        PostThumbnailImage postThumbnailImage =
            postThumbnailImageRepository.findByUrl(postReq.thumbnailUrl()).orElse(null);
        post.updateDetails(postReq.title(), postReq.content(), new CategoryId(postReq.categoryId()),
            postReq.hashtagIds().stream().map(HashtagId::new).toList(), postReq.state(), postImages, postThumbnailImage);
        return PostUpdateResponse.from(post);
    }
}