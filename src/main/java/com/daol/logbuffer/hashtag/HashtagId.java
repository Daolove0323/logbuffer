package com.daol.logbuffer.hashtag;

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
public class HashtagId {

    @Column(name = "hashtag_id")
    private UUID value;

    public HashtagId(UUID value) {
        this.value = value;
    }

    public static HashtagId generate() {
        return new HashtagId(UUID.randomUUID());
    }
}