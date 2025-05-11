package com.daol.logbuffer.image.domain.profileimage;

import com.daol.logbuffer.image.domain.ImageId;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImageId extends ImageId {

    public ProfileImageId(UUID value) {
        super(value);
    }

    public static ProfileImageId generate() {
        return new ProfileImageId(UUID.randomUUID());
    }
}