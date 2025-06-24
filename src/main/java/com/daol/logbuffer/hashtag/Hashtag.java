package com.daol.logbuffer.hashtag;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class Hashtag {

    @EmbeddedId
    private HashtagId id;

    @Column(name = "name", unique = true)
    private String name;

    protected Hashtag() {
        this.id = HashtagId.generate();
    }

    private Hashtag(String name) {
        this.id = HashtagId.generate();
        this.name = name;
    }

    public static Hashtag create(String name) {
        return new Hashtag(name);
    }
}