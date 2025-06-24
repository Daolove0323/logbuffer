package com.daol.logbuffer.post.command;

import com.daol.logbuffer.member.domain.MemberId;
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
public class PostAuthorId implements Serializable {

    @Column(name = "post_author_id")
    private UUID value;

    public PostAuthorId(UUID value) {
        this.value = value;
    }

    public PostAuthorId(MemberId memberId) {
        this.value = memberId.getValue();
    }

    public static PostAuthorId generate() {
        return new PostAuthorId(UUID.randomUUID());
    }
}