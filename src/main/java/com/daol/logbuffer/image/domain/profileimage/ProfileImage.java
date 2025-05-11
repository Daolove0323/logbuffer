package com.daol.logbuffer.image.domain.profileimage;

import com.daol.logbuffer.image.domain.UploaderId;
import com.daol.logbuffer.image.domain.Image;
import com.daol.logbuffer.image.domain.postthumbnailimage.PostThumbnailImage;
import com.daol.logbuffer.member.MemberId;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage extends Image {

    @EmbeddedId
    private ProfileImageId id;

    @Embedded
    private MemberId memberId;

    public ProfileImage(UploaderId uploaderId) {
        super(uploaderId);
        this.id = ProfileImageId.generate();
    }

    public static ProfileImage create(UploaderId uploaderId) {
        return new ProfileImage(uploaderId);
    }

    public void setMemberId(MemberId memberId) {
        this.memberId = memberId;
    }
}