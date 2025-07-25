package com.daol.logbuffer.comment.comment.infra;

import static com.daol.logbuffer.comment.comment.query.QCommentData.commentData;
import static com.daol.logbuffer.image.domain.QProfileImage.profileImage;
import static com.daol.logbuffer.member.domain.QMember.member;

import com.daol.logbuffer._common.config.ImageConfig;
import com.daol.logbuffer.comment.comment.command.CommentState;
import com.daol.logbuffer.comment.comment.query.CommentDataRepository;
import com.daol.logbuffer.comment.comment.query.CommentResponse;
import com.daol.logbuffer.comment.comment.query.QCommentResponse;
import com.daol.logbuffer.member.query.QCommentMemberResponse;
import com.daol.logbuffer.post.command.PostId;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentDataRepositoryImpl implements CommentDataRepository {

    private final JPAQueryFactory queryFactory;
    private final ImageConfig imageConfig;
    private static final String HIDDEN_COMMENT_MESSAGE = "이 댓글은 숨겨진 댓글입니다.";

    @Override
    public Page<CommentResponse> findCommentsForAdmin(PostId postId, Pageable pageable) {
        List<CommentResponse> res = queryFactory.
            select(new QCommentResponse(
                commentData.id.value,
                commentData.content,
                commentData.state,
                new QCommentMemberResponse(
                    authorIdExpr(),
                    member.name,
                    authorTypeExpr(),
                    profileImageUrlExpr()),
                commentData.createdDate,
                commentData.modifiedDate))
            .from(commentData)
            .leftJoin(member).on(commentData.author.memberAuthorId.value.eq(member.id.value))
            .leftJoin(profileImage).on(member.id.eq(profileImage.memberId))
            .where(commentData.postId.eq(postId))
            .orderBy(commentData.createdDate.asc())
            .fetch();
        Long total = queryFactory
            .select(commentData.count())
            .from(commentData)
            .where((commentData.postId.eq(postId)))
            .fetchOne();
        return new PageImpl<>(res, pageable, total);
    }

    @Override
    public Page<CommentResponse> findCommentsForMember(PostId postId, Pageable pageable) {
        List<CommentResponse> res = queryFactory.
            select(new QCommentResponse(
                commentData.id.value,
                visibleContentExpr(),
                commentData.state,
                new QCommentMemberResponse(
                    authorIdExpr(),
                    member.name,
                    authorTypeExpr(),
                    profileImageUrlExpr()),
                commentData.createdDate,
                commentData.modifiedDate))
            .from(commentData)
            .leftJoin(member).on(commentData.author.memberAuthorId.value.eq(member.id.value))
            .leftJoin(profileImage).on(member.id.eq(profileImage.memberId))
            .where(commentData.state.eq(CommentState.PUBLISHED)
                .and(commentData.postId.eq(postId)))
            .orderBy(commentData.createdDate.asc())
            .fetch();
        Long total = queryFactory
            .select(commentData.count())
            .from(commentData)
            .where((commentData.postId.eq(postId)))
            .fetchOne();
        return new PageImpl<>(res, pageable, total);
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

    private Expression<UUID> authorIdExpr() {
        return new CaseBuilder()
            .when(commentData.author.memberAuthorId.value.isNotNull())
            .then(commentData.author.memberAuthorId.value)
            .otherwise(commentData.author.guestAuthorId.value);
    }

    private Expression<String> authorTypeExpr() {
        return new CaseBuilder()
            .when(commentData.author.memberAuthorId.value.isNotNull()).then("member")
            .otherwise("guest");
    }

    private Expression<String> visibleContentExpr() {
        return new CaseBuilder()
            .when(commentData.state.eq(CommentState.PUBLISHED))
            .then(commentData.content)
            .otherwise(HIDDEN_COMMENT_MESSAGE);
    }
}