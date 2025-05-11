package com.daol.logbuffer.post.command;

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
public class PostId {

    @Column(name = "post_id")
    private UUID value;

    public PostId(UUID value) {
        this.value = value;
    }

    public static PostId generate() {
        return new PostId(UUID.randomUUID());
    }
}