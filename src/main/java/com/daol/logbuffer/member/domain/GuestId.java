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
@EqualsAndHashCode(callSuper = false)
public class GuestId extends UserId {

    @Column(name = "guest_id")
    private UUID value;

    public GuestId(UUID value) {
        this.value = value;
    }

    public GuestId(String value) {
        this.value = UUID.fromString(value);
    }

    public static GuestId generate() {
        return new GuestId(UUID.randomUUID());
    }
}