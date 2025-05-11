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
public class UploaderId {

    @Column(name = "uploader_id")
    private UUID value;

    public UploaderId(UUID value) {
        this.value = value;
    }

    public static UploaderId generate() {
        return new UploaderId(UUID.randomUUID());
    }
}