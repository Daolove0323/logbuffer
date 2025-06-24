package com.daol.logbuffer.image.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class ImageId {

    @Column(name = "image_id")
    private UUID value;

    public ImageId(UUID value) {
        this.value = value;
    }

    public static ImageId generate() {
        return new ImageId(UUID.randomUUID());
    }
}