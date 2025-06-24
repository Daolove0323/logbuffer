package com.daol.logbuffer.member.infra;

import com.daol.logbuffer.member.domain.PasswordHasher;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Argon2PasswordHasher implements PasswordHasher {

    private final Argon2 argon2;
    private final Integer iterations;
    private final Integer memory;
    private final Integer parallelism;

    public Argon2PasswordHasher(
        @Value("${security.password.iterations:10}") Integer passwordIterations,
        @Value("${security.password.memory:65536}") Integer passwordMemory,
        @Value("${security.password.parallelism:1}") Integer passwordParallelism) {
        this.iterations = passwordIterations;
        this.memory = passwordMemory;
        this.parallelism = passwordParallelism;
        this.argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    }

    public String encode(String rawPassword) {
        return argon2.hash(iterations, memory, parallelism, rawPassword.toCharArray());
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return argon2.verify(encodedPassword, rawPassword.toCharArray());
    }
}