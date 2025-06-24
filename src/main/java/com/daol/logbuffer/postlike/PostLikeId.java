package com.daol.logbuffer.postlike;

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
public class PostLikeId {

    @Column(name = "post_like_id")
    private UUID value;

    public PostLikeId(UUID value) {
        this.value = value;
    }

    public static PostLikeId generate() {
        return new PostLikeId(UUID.randomUUID());
    }
}