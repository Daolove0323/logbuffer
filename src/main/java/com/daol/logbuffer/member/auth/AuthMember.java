package com.daol.logbuffer.member.auth;

import com.daol.logbuffer.member.common.Grade;
import com.daol.logbuffer.member.domain.MemberId;

public record AuthMember(MemberId memberId, Grade grade) {

    public boolean hasRequiredGrade(Grade requiredGrade) {
        return this.grade.equals(Grade.ADMIN) || this.grade.equals(requiredGrade);
    }
}