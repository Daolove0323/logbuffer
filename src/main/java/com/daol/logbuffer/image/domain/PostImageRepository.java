package com.daol.logbuffer.image.domain;

import com.daol.logbuffer.post.command.PostId;
import java.util.List;
import java.util.Optional;

// Todo: 모든 리포지토리 CRUD, JPA Repository 선택
public interface PostImageRepository extends ImageRepository<PostImage> {

    Optional<PostImage> findByFileName(String fileName);

    List<PostImage> findAllByPostId(PostId postId);
}