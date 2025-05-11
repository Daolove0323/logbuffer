package com.daol.logbuffer.member;

import com.daol.logbuffer.image.Image;
import com.daol.logbuffer.image.UploaderId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage extends Image {

    @JoinColumn(name = "member_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    public ProfileImage(String name, UploaderId uploaderId) {
        super(name, uploaderId);
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
