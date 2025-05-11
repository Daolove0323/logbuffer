package com.daol.logbuffer.member;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @EmbeddedId
    private MemberId id;

    @Column(name = "email", unique = true, length = 40)
    @NotNull
    private String email;

    @Column(name = "name", length = 20)
    @NotNull
    private String name;

    @Column(name = "login_type")
    @Enumerated(EnumType.STRING)
    @NotNull
    private LoginType loginType;

    @Column(name = "grade")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Grade grade;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Builder
    private Member(String email, String name, LoginType loginType) {
        this.id = MemberId.generate();
        this.email = email;
        this.name = name;
        this.loginType = loginType;
        this.grade = Grade.NORMAL;
    }
}