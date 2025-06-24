package com.daol.logbuffer.image.domain;

import com.daol.logbuffer.member.domain.MemberId;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage extends Image {

    @Embedded
    private MemberId memberId;

    public ProfileImage(UploaderId uploaderId) {
        super(uploaderId);
    }

    public static ProfileImage create(UploaderId uploaderId) {
        return new ProfileImage(uploaderId);
    }

    public void changeMemberId(MemberId memberId) {
        this.memberId = memberId;
    }
}