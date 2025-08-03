package com.daol.logbuffer.image.domain;

import com.daol.logbuffer.member.domain.MemberId;
import com.daol.logbuffer.post.command.PostAuthorId;
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

    public UploaderId(MemberId memberId) {
        this.value = memberId.getValue();
    }

    public static UploaderId generate() {
        return new UploaderId(UUID.randomUUID());
    }

    public static UploaderId from(PostAuthorId authorId) {
        return new UploaderId(authorId.getValue());
    }
}