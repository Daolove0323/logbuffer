package com.daol.logbuffer.image.domain.postimage;

import com.daol.logbuffer.image.domain.ImageId;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class PostImageId extends ImageId {

    public PostImageId(UUID value) {
        super(value);
    }

    public static PostImageId generate() {
        return new PostImageId(UUID.randomUUID());
    }
}
