package com.daol.logbuffer.comment.comment.command;

import com.daol.logbuffer.member.domain.GuestId;
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
public class GuestCommentAuthorId {

    @Column(name = "guest_author_id")
    private UUID value;

    public GuestCommentAuthorId(UUID value) {
        this.value = value;
    }

    public GuestCommentAuthorId(GuestId guestId) {
        this.value = guestId.getValue();
    }

    public static GuestCommentAuthorId generate() {
        return new GuestCommentAuthorId(UUID.randomUUID());
    }
}