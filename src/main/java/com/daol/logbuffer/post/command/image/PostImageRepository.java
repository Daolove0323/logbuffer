package com.daol.logbuffer.post.command.image;

import com.daol.logbuffer.image.ImageRepository;
import java.util.Optional;

public interface PostImageRepository extends ImageRepository<PostImage> {

    Optional<PostImage> findByUrl(String url);
}
