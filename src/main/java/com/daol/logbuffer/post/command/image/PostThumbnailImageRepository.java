package com.daol.logbuffer.post.command.image;

import com.daol.logbuffer.image.ImageRepository;
import java.util.Optional;

public interface PostThumbnailImageRepository extends ImageRepository<PostThumbnailImage> {

    Optional<PostThumbnailImage> findByUrl(String url);
}
