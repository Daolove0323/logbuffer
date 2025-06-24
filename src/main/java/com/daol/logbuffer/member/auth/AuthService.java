package com.daol.logbuffer.member.auth;

import com.daol.logbuffer._common.exception.EntityNotFoundException;
import com.daol.logbuffer._common.exception.UnauthenticatedException;
import com.daol.logbuffer._common.exception.UnauthorizedException;
import com.daol.logbuffer.member.common.Grade;
import com.daol.logbuffer.member.domain.Member;
import com.daol.logbuffer.member.domain.MemberRepository;
import com.daol.logbuffer.member.domain.PasswordHasher;
import com.daol.logbuffer.member.domain.TokenGenerator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordHasher passwordHasher;
    private final TokenGenerator tokenGenerator;

    @Transactional
    public Token signUp(SignUpRequest signUpReq) {
        Member member = memberRepository.save(
            Member.createNormalMember(signUpReq.email(), signUpReq.name(), signUpReq.rawPassword(), passwordHasher));
        return member.generateToken(tokenGenerator);
    }

    @Transactional(readOnly = true)
    public Token login(LoginRequest loginReq) {
        Member member = memberRepository.findByEmail(loginReq.email())
            .orElseThrow(() -> new EntityNotFoundException("ID에 해당하는 멤버를 찾을 수 없습니다."));
        member.validatePassword(loginReq.rawPassword(), passwordHasher);
        return member.generateToken(tokenGenerator);
    }

    @Transactional(readOnly = true)
    public void validateMemberExists(AuthMember member) {
        if (member.memberId() == null) {
            throw new UnauthenticatedException("토큰에서 ID 정보를 찾을 수 없습니다.");
        }
        if (member.grade() == null) {
            throw new UnauthenticatedException("토큰에서 등급 정보를 찾을 수 없습니다.");
        }
        memberRepository.findById(member.memberId()).
            orElseThrow(() -> new UnauthenticatedException("ID에 해당하는 멤버를 찾을 수 없습니다."));
    }

    public void validateAuthorization(AuthMember member, Grade requiredGrade) {
        if (!member.hasRequiredGrade(requiredGrade)) {
            throw new UnauthorizedException("사용자의 권한이 부족합니다.");
        }
    }
}