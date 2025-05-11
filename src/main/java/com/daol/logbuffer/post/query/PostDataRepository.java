package com.daol.logbuffer.post.query;

import com.daol.logbuffer.category.CategoryId;
import com.daol.logbuffer.hashtag.HashtagId;
import com.daol.logbuffer.post.command.PostId;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostDataRepository extends JpaRepository<PostData, PostId> {

    @Query("""
        select new com.daol.logbuffer.post.query.PostPreviewResponse(
                 p.id.value,
                 p.title,
                 p.content.text,
                 new com.daol.logbuffer.member.PostMemberResponse(
                     m.id.value,
                     m.name,
                     pi.url),
                 p.state,
                 pt.url,
                 pm.likeCount,
                 pm.commentCount,
                 c.name,
                 pm.hashtags,
                 p.createdDate,
                 p.modifiedDate)
                 from PostData p
                 left join Member m on p.postAuthorId.value = m.id.value
                 left join  PostMeta pm on p.id.value = pm.id.value
                 left join Category c on p.categoryId.value = c.id.value
                 left join PostThumbnailImage pt on pt.id.value = p.id.value
                 left join ProfileImage pi on m.id.value = pi.uploaderId.value
                 where p.state = 'PUBLISHED'
                 order by p.createdDate desc
        """)
    Page<PostPreviewResponse> findPublishedPosts(Pageable pageable);

    @Query("""
         select new com.daol.logbuffer.post.query.PostPreviewResponse(
                  p.id.value,
                  p.title,
                  p.content.text,
                  new com.daol.logbuffer.member.PostMemberResponse(
                      m.id.value,
                      m.name,
                      m.profileImage.url),
                  p.state,
                  p.postThumbnailImage.url,
                  pm.likeCount,
                  pm.commentCount,
                  c.name,
                 pm.hashtags,
                  p.createdDate,
                  p.modifiedDate)
                  from PostData p
                  left join Member m on p.postAuthorId.value = m.id.value
                  left join p.hashtagIds h
                  left join  PostMeta pm on p.id.value = pm.id.value
                  left join Category c on p.categoryId.value = c.id.value
                  where p.state = 'PUBLISHED'
                  and p.categoryId = :categoryId
                  order by p.createdDate desc
        """)
    Page<PostPreviewResponse> findPublishedPostsByCategory(CategoryId categoryId, Pageable pageable);

    @Query("""
         select new com.daol.logbuffer.post.query.PostPreviewResponse(
                  p.id.value,
                  p.title,
                  p.content.text,
                  new com.daol.logbuffer.member.PostMemberResponse(
                      m.id.value,
                      m.name,
                      m.profileImage.url),
                  p.state,
                  p.postThumbnailImage.url,
                  pm.likeCount,
                  pm.commentCount,
                  c.name,
                 pm.hashtags,
                  p.createdDate,
                  p.modifiedDate)
                  from PostData p
                  left join Member m on p.postAuthorId.value = m.id.value
                  left join p.hashtagIds h
                  left join  PostMeta pm on p.id.value = pm.id.value
                  left join Category c on p.categoryId.value = c.id.value
                  where p.state = 'PUBLISHED'
                  and h = :hashtagId
                  order by p.createdDate desc
        """)
    Page<PostPreviewResponse> findPublishedPostsByHashtag(HashtagId hashtagId, Pageable pageable);

    @Query("""
        select new com.daol.logbuffer.post.query.PostPreviewResponse(
                 p.id.value,
                 p.title,
                 p.content.text,
                 new com.daol.logbuffer.member.PostMemberResponse(
                     m.id.value,
                     m.name,
                     m.profileImage.url),
                 p.state,
                 p.postThumbnailImage.url,
                 pm.likeCount,
                 pm.commentCount,
                 c.name,
                 pm.hashtags,
                 p.createdDate,
                 p.modifiedDate)
                         from PostData p
                 left join Member m on p.postAuthorId.value = m.id
                 left join  PostMeta pm on p.id.value = pm.id.value
                 left join Category c on p.categoryId = c.id
                 where p.state = 'PUBLISHED'
                    and p.title like concat('%', :title, '%')
                 order by p.createdDate desc
        """)
    Page<PostPreviewResponse> findPublishedPostsByTitleContaining(String title, Pageable pageable);

    @Query("""
        select new com.daol.logbuffer.post.query.PostDetailResponse(
                p.id.value,
                p.title,
                p.content.text,
                new com.daol.logbuffer.member.PostMemberResponse(
                      m.id.value,
                      m.name,
                      m.profileImage.url),
                p.state,
                pt.url,
                pm.likeCount,
                pm.commentCount,
                c.name,
                pm.hashtags,
                p.createdDate,
                p.modifiedDate
                )
        from PostData p
                left join Member m on p.postAuthorId.value = m.id.value
                left join Category c on p.categoryId.value = c.id.value
                left join  PostMeta pm on p.id.value = pm.id.value
                left join PostThumbnailImage pt on pt.id.value = p.id.value
                where p.id = :postId
        """)
    Optional<PostDetailResponse> findDetailPost(PostId postId);
}