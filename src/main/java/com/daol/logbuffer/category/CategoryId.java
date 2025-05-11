package com.daol.logbuffer.category;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class CategoryId implements Serializable {

    @Column(name = "category_id")
    private UUID value;

    public CategoryId(UUID value) {
        this.value = value;
    }

    public static CategoryId generate() {
        return new CategoryId(UUID.randomUUID());
    }
}