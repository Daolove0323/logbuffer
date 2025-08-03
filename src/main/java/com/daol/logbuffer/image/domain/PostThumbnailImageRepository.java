package com.daol.logbuffer.image.domain;

import com.daol.logbuffer.post.command.PostId;
import java.util.Optional;

public interface PostThumbnailImageRepository extends ImageRepository<PostThumbnailImage> {

    Optional<PostThumbnailImage> findByPostId(PostId postId);
}