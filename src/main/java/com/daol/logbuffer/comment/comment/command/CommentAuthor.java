package com.daol.logbuffer.comment.comment.command;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class CommentAuthor {

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "member_author_id"))
    private MemberCommentAuthorId memberAuthorId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "guest_author_id"))
    private GuestCommentAuthorId guestAuthorId;

    public CommentAuthor(MemberCommentAuthorId memberAuthorId) {
        this.memberAuthorId = memberAuthorId;
    }

    public CommentAuthor(GuestCommentAuthorId guestAuthorId) {
        this.guestAuthorId = guestAuthorId;
    }
}