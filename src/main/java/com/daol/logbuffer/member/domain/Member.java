package com.daol.logbuffer.member.domain;

import com.daol.logbuffer._common.exception.InvalidRequestException;
import com.daol.logbuffer.member.auth.Token;
import com.daol.logbuffer.member.common.Grade;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @EmbeddedId
    private MemberId id;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "email", unique = true, length = 40)
    private String email;

    @Column(name = "hashed_password")
    private String hashedPassword;

    @Column(name = "grade")
    @Enumerated(EnumType.STRING)
    private Grade grade;

    // Todo: Oauth 추가에 따른 LoginType 고려

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    private Member(String email, String name, String hashedPassword) {
        this.id = MemberId.generate();
        this.email = email;
        this.name = name;
        this.hashedPassword = hashedPassword;
        this.grade = Grade.NORMAL;
    }

    public static Member createNormalMember(String email, String name, String rawPassword, PasswordHasher passwordHasher) {
        String encodedPassword = passwordHasher.encode(rawPassword);
        return new Member(email, name, encodedPassword);
    }

    public Token generateToken(TokenGenerator tokenGenerator) {
        return tokenGenerator.generateToken(this.getId(), this.grade);
    }

    public void validatePassword(String rawPassword, PasswordHasher passwordHasher) {
        if (!passwordHasher.matches(rawPassword, this.hashedPassword)) {
            throw new InvalidRequestException("비밀번호가 일치하지 않습니다.");
        }
    }
}