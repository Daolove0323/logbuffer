package com.daol.logbuffer.postmeta;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class HashtagName {

    String value;

    public HashtagName(String value) {
        this.value = value;
    }
}