package com.daol.logbuffer.member.infra;

import static com.daol.logbuffer.image.domain.QProfileImage.profileImage;
import static com.daol.logbuffer.member.domain.QMember.member;

import com.daol.logbuffer._common.config.ImageConfig;
import com.daol.logbuffer.member.domain.MemberId;
import com.daol.logbuffer.member.query.LoginMemberResponse;
import com.daol.logbuffer.member.query.QLoginMemberResponse;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRepositoryImpl {

    private final JPAQueryFactory queryFactory;
    private final ImageConfig imageConfig;

    public LoginMemberResponse findLoginMemberId(MemberId memberId) {
        return queryFactory
            .select(new QLoginMemberResponse(
                member.id.value,
                member.email,
                member.name,
                member.grade,
                profileImageUrlExpr()))
            .from(member)
            .leftJoin(profileImage).on(member.id.eq(profileImage.memberId))
            .where(member.id.eq(memberId))
            .fetchOne();
    }

    private Expression<String> profileImageUrlExpr() {
        return Expressions.cases()
            .when(profileImage.fileName.isNull())
            .then(Expressions.nullExpression(String.class))
            .otherwise(
                Expressions.stringTemplate("CONCAT({0}, '/', {1}, '/', {2})",
                    imageConfig.getImageApiUrl(),
                    imageConfig.getProfileImageDirectory(),
                    profileImage.fileName)
            );
    }
}