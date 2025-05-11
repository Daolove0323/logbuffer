package com.daol.logbuffer.member;

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

    private MemberId(UUID value) {
        this.value = value;
    }

    public static MemberId generate() {
        return new MemberId(UUID.randomUUID());
    }
}
