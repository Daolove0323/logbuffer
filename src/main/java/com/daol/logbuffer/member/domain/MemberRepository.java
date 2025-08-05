package com.daol.logbuffer.member.domain;

import com.daol.logbuffer.member.query.LoginMemberResponse;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, MemberId> {

    Optional<Member> findByEmail(String email);

    LoginMemberResponse findLoginMemberId(MemberId memberId);
}