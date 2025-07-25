package com.daol.logbuffer.comment.comment.command;

import com.daol.logbuffer.member.domain.MemberId;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
public class MemberCommentAuthorId extends CommentAuthorId {

    private UUID value;

    public MemberCommentAuthorId(UUID value) {
        this.value = value;
    }

    public MemberCommentAuthorId(MemberId memberId) {
        this.value = memberId.getValue();
    }

    public static MemberCommentAuthorId generate() {
        return new MemberCommentAuthorId(UUID.randomUUID());
    }
}