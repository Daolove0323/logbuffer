package com.daol.logbuffer.image.domain.postthumbnailimage;

import com.daol.logbuffer.image.domain.ImageRepository;
import java.util.Optional;

public interface PostThumbnailImageRepository extends ImageRepository<PostThumbnailImage, PostThumbnailImageId> {

    Optional<PostThumbnailImage> findByUrl(String url);
}
