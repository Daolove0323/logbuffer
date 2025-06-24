package com.daol.logbuffer.member.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record LoginRequest(
    
    @NotNull
    @Email
    String email,

    @NotNull
    @Length(min = 8, max = 20)
    String rawPassword
) {

}