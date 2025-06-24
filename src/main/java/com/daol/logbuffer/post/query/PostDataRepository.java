package com.daol.logbuffer.post.query;

import com.daol.logbuffer.category.CategoryId;
import com.daol.logbuffer.hashtag.HashtagId;
import com.daol.logbuffer.post.command.PostId;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostDataRepository {

    Page<PostPreviewResponse> findPosts(Pageable pageable);

    Page<PostPreviewResponse> findPublishedPostsByFilter(Pageable pageable, CategoryId categoryId, HashtagId hashtagId, String keyword);

    Page<PostPreviewResponse> findPostsByFilter(Pageable pageable, CategoryId categoryId, HashtagId hashtagId, String keyword);

    Page<PostPreviewResponse> findPublishedPosts(Pageable pageable);

    Page<PostPreviewResponse> findPostsByCategory(CategoryId categoryId, Pageable pageable);

    Page<PostPreviewResponse> findPublishedPostsByCategory(CategoryId categoryId, Pageable pageable);

    Page<PostPreviewResponse> findPostsByHashtag(HashtagId hashtagId, Pageable pageable);

    Page<PostPreviewResponse> findPublishedPostsByHashtag(HashtagId hashtagId, Pageable pageable);

    Page<PostPreviewResponse> findPostsByTitleContaining(String title, Pageable pageable);

    Page<PostPreviewResponse> findPublishedPostsByTitleContaining(String title, Pageable pageable);

    Optional<PostDetailResponse> findDetailPost(PostId postId);
}