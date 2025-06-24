package com.daol.logbuffer.member.domain;

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
public class MemberId {

    @Column(name = "member_id")
    private UUID value;

    public MemberId(UUID value) {
        this.value = value;
    }

    public MemberId(String value) {
        this.value = UUID.fromString(value);
    }

    public static MemberId generate() {
        return new MemberId(UUID.randomUUID());
    }
}