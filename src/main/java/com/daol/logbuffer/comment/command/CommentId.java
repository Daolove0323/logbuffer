package com.daol.logbuffer.comment.command;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class CommentId implements Serializable {

    @Column(name = "comment_id")
    private UUID value;

    public CommentId(UUID value) {
        this.value = value;
    }

    public static CommentId generate() {
        return new CommentId(UUID.randomUUID());
    }
}