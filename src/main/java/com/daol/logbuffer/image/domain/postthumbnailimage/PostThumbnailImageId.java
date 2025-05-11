package com.daol.logbuffer.image.domain.postthumbnailimage;

import com.daol.logbuffer.image.domain.ImageId;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostThumbnailImageId extends ImageId {

    public PostThumbnailImageId(UUID value) {
        super(value);
    }

    public static PostThumbnailImageId generate() {
        return new PostThumbnailImageId(UUID.randomUUID());
    }
}