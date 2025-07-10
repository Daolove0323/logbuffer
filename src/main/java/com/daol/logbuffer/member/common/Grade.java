package com.daol.logbuffer.member.common;

import lombok.Getter;

@Getter
public enum Grade {
    ADMIN(3),
    NORMAL(2),
    GUEST(1);

    private final int level;

    Grade(int level) {
        this.level = level;
    }
}