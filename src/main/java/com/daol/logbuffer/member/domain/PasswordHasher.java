package com.daol.logbuffer.member.domain;

public interface PasswordHasher {

    String encode(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}