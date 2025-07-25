package com.daol.logbuffer.member.auth;

import com.daol.logbuffer._common.exception.InvalidRequestException;
import com.daol.logbuffer.member.common.Grade;
import com.daol.logbuffer.member.domain.GuestId;
import com.daol.logbuffer.member.domain.MemberId;
import com.daol.logbuffer.member.domain.UserId;

public record CurrentUser(UserId userId, Grade grade) {

    public boolean hasRequiredGrade(Grade requiredGrade) {
        return this.grade.getLevel() >= requiredGrade.getLevel();
    }

    public boolean isGuest() {
        return this.grade.equals(Grade.GUEST);
    }

    public boolean isMember() {
        return this.grade.equals(Grade.NORMAL);
    }

    public boolean isAdmin() {
        return this.grade.equals(Grade.ADMIN);
    }

    public MemberId getMemberId() {
        if (userId instanceof MemberId memberId) {
            return memberId;
        }
        throw new InvalidRequestException("현재 사용자는 회원이 아닙니다");
    }

    public GuestId getGuestId() {
        if (userId instanceof GuestId guestId) {
            return guestId;
        }
        throw new InvalidRequestException("현재 사용자는 게스트가 아닙니다");
    }
}