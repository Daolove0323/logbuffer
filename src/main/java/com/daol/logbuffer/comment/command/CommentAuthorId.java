package com.daol.logbuffer.comment.command;

import com.daol.logbuffer.member.domain.MemberId;
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
public class CommentAuthorId {

    @Column(name = "author_id")
    private UUID value;

    public CommentAuthorId(UUID value) {
        this.value = value;
    }

    public CommentAuthorId(MemberId memberId) {
        this.value = memberId.getValue();
    }

    public static CommentAuthorId generate() {
        return new CommentAuthorId(UUID.randomUUID());
    }
}