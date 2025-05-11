package com.daol.logbuffer.postlike.command;

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
public class PostLikerId {

    @Column(name = "liker_id")
    private UUID value;

    public PostLikerId(UUID value) {
        this.value = value;
    }

    public static PostLikerId generate() {
        return new PostLikerId(UUID.randomUUID());
    }
}
