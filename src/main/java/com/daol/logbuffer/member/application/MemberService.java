package com.daol.logbuffer.member.application;

import com.daol.logbuffer.member.auth.CurrentUser;
import com.daol.logbuffer.member.domain.MemberId;
import com.daol.logbuffer.member.domain.MemberRepository;
import com.daol.logbuffer.member.query.LoginMemberResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public LoginMemberResponse getLoginMember(CurrentUser user) {
        MemberId memberId = user.getMemberId();
        return memberRepository.findLoginMemberId(memberId);
    }
}