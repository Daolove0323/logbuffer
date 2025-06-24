package com.daol.logbuffer.member.application;

import com.daol.logbuffer._common.exception.EntityNotFoundException;
import com.daol.logbuffer.member.domain.Member;
import com.daol.logbuffer.member.domain.MemberId;
import com.daol.logbuffer.member.domain.MemberRepository;
import com.daol.logbuffer.member.query.MemberDetailResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberService {

    private final MemberRepository memberRepository;

    // Todo: 멤버 상세 조회 api 추가
    @Transactional(readOnly = true)
    public MemberDetailResponse getMemberDetail(MemberId memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new EntityNotFoundException("ID에 해당하는 멤버를 찾을 수 없습니다."));
        return null;
    }
}