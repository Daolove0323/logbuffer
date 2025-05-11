package com.daol.logbuffer.image.domain.postimage;

import com.daol.logbuffer.image.domain.ImageRepository;
import java.util.Optional;

public interface PostImageRepository extends ImageRepository<PostImage, PostImageId> {

    Optional<PostImage> findByUrl(String url);
}