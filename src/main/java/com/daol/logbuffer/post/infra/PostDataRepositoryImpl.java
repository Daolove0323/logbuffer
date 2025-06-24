package com.daol.logbuffer.post.infra;

import static com.daol.logbuffer.category.QCategory.category;
import static com.daol.logbuffer.image.domain.QPostThumbnailImage.postThumbnailImage;
import static com.daol.logbuffer.image.domain.QProfileImage.profileImage;
import static com.daol.logbuffer.member.domain.QMember.member;
import static com.daol.logbuffer.post.query.QPostData.postData;
import static com.daol.logbuffer.postmeta.QPostMeta.postMeta;

import com.daol.logbuffer._common.config.ImageConfig;
import com.daol.logbuffer.category.CategoryId;
import com.daol.logbuffer.hashtag.HashtagId;
import com.daol.logbuffer.image.common.ImageType;
import com.daol.logbuffer.member.query.QPostMemberResponse;
import com.daol.logbuffer.post.command.PostId;
import com.daol.logbuffer.post.command.PostState;
import com.daol.logbuffer.post.query.PostDataRepository;
import com.daol.logbuffer.post.query.PostDetailResponse;
import com.daol.logbuffer.post.query.PostPreviewResponse;
import com.daol.logbuffer.post.query.QPostDetailResponse;
import com.daol.logbuffer.post.query.QPostPreviewResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

// Todo: UrlBuilder 클래스를 만들어 이미지 URL 생성 로직 분리
@Repository
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDataRepositoryImpl implements PostDataRepository {

    @Value("${post.preview-length}")
    private int PREVIEW_CONTENT_LENGTH;

    private final ImageConfig imageConfig;
    private final JPAQueryFactory queryFactory;

    private JPAQuery<PostPreviewResponse> createPostPreviewQuery() {
        return queryFactory
            .select(new QPostPreviewResponse(
                postData.id.value,
                postData.title,
                postData.content.text.substring(0, PREVIEW_CONTENT_LENGTH),
                new QPostMemberResponse(
                    postData.postAuthorId.value,
                    member.name,
                    createProfileImageUrlExpression()),
                postData.state,
                createPostThumbnailUrlExpression(),
                postMeta.likeCount,
                postMeta.commentCount,
                category.name,
                postMeta.hashtags,
                postData.createdDate,
                postData.modifiedDate
            ))
            .from(postData)
            .leftJoin(member).on(postData.postAuthorId.value.eq(member.id.value))
            .leftJoin(profileImage).on(member.id.eq(profileImage.memberId))
            .leftJoin(postThumbnailImage).on(postData.id.value.eq(postThumbnailImage.id.value))
            .leftJoin(postMeta).on(postData.id.eq(postMeta.id))
            .leftJoin(category).on(postData.categoryId.eq(category.id));
    }

    private Page<PostPreviewResponse> findPostsWithCondition(Predicate condition, Pageable pageable) {
        List<PostPreviewResponse> res = createPostPreviewQuery()
            .where(condition)
            .orderBy(postData.createdDate.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = countPosts(condition);
        return new PageImpl<>(res, pageable, total);
    }

    @Override
    public Page<PostPreviewResponse> findPublishedPostsByFilter(Pageable pageable, CategoryId categoryId, HashtagId hashtagId, String keyword) {
        BooleanBuilder builder = new BooleanBuilder();
        if (categoryId != null) {
            builder.and(postData.categoryId.eq(categoryId));
        }
        if (hashtagId != null) {
            builder.and(postData.hashtagIds.contains(hashtagId));
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            builder.and(postData.title.containsIgnoreCase(keyword));
        }
        builder.and(postData.state.eq(PostState.PUBLISHED));
        return findPostsWithCondition(builder, pageable);
    }

    @Override
    public Page<PostPreviewResponse> findPostsByFilter(Pageable pageable, CategoryId categoryId, HashtagId hashtagId, String keyword) {
        BooleanBuilder builder = new BooleanBuilder();
        if (categoryId != null) {
            builder.and(postData.categoryId.eq(categoryId));
        }
        if (hashtagId != null) {
            builder.and(postData.hashtagIds.contains(hashtagId));
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            builder.and(postData.title.containsIgnoreCase(keyword));
        }
        return findPostsWithCondition(builder, pageable);
    }

    @Override
    public Page<PostPreviewResponse> findPosts(Pageable pageable) {
        return findPostsWithCondition(null, pageable);
    }

    @Override
    public Page<PostPreviewResponse> findPublishedPosts(Pageable pageable) {
        return findPostsWithCondition(postData.state.eq(PostState.PUBLISHED), pageable);
    }

    @Override
    public Page<PostPreviewResponse> findPostsByCategory(CategoryId categoryId, Pageable pageable) {
        return findPostsWithCondition(postData.categoryId.eq(categoryId), pageable);
    }

    @Override
    public Page<PostPreviewResponse> findPublishedPostsByCategory(CategoryId categoryId, Pageable pageable) {
        return findPostsWithCondition(
            postData.state.eq(PostState.PUBLISHED).and(postData.categoryId.eq(categoryId)), pageable);

    }

    @Override
    public Page<PostPreviewResponse> findPostsByHashtag(HashtagId hashtagId, Pageable pageable) {
        return findPostsWithCondition((postData.hashtagIds.contains(hashtagId)), pageable);
    }

    @Override
    public Page<PostPreviewResponse> findPublishedPostsByHashtag(HashtagId hashtagId, Pageable pageable) {
        return findPostsWithCondition(postData.state.eq(PostState.PUBLISHED)
            .and(postData.hashtagIds.contains(hashtagId)), pageable);
    }

    @Override
    public Page<PostPreviewResponse> findPostsByTitleContaining(String title, Pageable pageable) {
        return findPostsWithCondition(postData.title.containsIgnoreCase(title), pageable);

    }

    @Override
    public Page<PostPreviewResponse> findPublishedPostsByTitleContaining(String title, Pageable pageable) {
        return findPostsWithCondition(postData.state.eq(PostState.PUBLISHED)
            .and(postData.title.containsIgnoreCase(title)), pageable);
    }

    @Override
    public Optional<PostDetailResponse> findDetailPost(PostId postId) {
        PostDetailResponse res = queryFactory
            .select(new QPostDetailResponse(
                postData.id.value,
                postData.title,
                postData.content.text,
                new QPostMemberResponse(
                    postData.postAuthorId.value,
                    member.name,
                    createProfileImageUrlExpression()),
                postData.state,
                createPostThumbnailUrlExpression(),
                postMeta.likeCount,
                postMeta.commentCount,
                category.name,
                postMeta.hashtags,
                postData.createdDate,
                postData.modifiedDate))
            .from(postData)
            .leftJoin(member).on(postData.postAuthorId.value.eq(member.id.value))
            .leftJoin(profileImage).on(member.id.eq(profileImage.memberId))
            .leftJoin(postThumbnailImage).on(postData.id.value.eq(postThumbnailImage.id.value))
            .leftJoin(postMeta).on(postData.id.eq(postMeta.id))
            .leftJoin(category).on(postData.categoryId.eq(category.id))
            .fetchOne();
        return Optional.ofNullable(res);
    }

    private Expression<String> createProfileImageUrlExpression() {
        return Expressions.cases()
            .when(profileImage.fileName.isNull())
            .then(Expressions.nullExpression(String.class))
            .otherwise(
                Expressions.stringTemplate("CONCAT({0}, '/', {1}, '/', {2})",
                    imageConfig.getImageApiUrl(),
                    ImageType.PROFILE.name(),
                    profileImage.fileName)
            );
    }

    private Expression<String> createPostThumbnailUrlExpression() {
        return Expressions.cases()
            .when(postThumbnailImage.fileName.isNull())
            .then(Expressions.nullExpression(String.class))
            .otherwise(
                Expressions.stringTemplate("CONCAT({0}, '/', {1}, '/', {2})",
                    imageConfig.getImageApiUrl(),
                    ImageType.POST_THUMBNAIL.name(),
                    postThumbnailImage.fileName)
            );
    }

    private Long countPosts(Predicate condition) {
        return queryFactory
            .select(postData.count())
            .from(postData)
            .where(condition)
            .fetchOne();
    }
}