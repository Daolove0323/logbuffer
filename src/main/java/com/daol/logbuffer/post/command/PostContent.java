package com.daol.logbuffer.post.command;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class PostContent {

    @Column(name = "content", length = 10000)
    private String text;

    public PostContent(String text) {
        this.text = text;
    }
}