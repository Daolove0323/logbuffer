package com.daol.logbuffer.comment.query;

import com.daol.logbuffer.comment.command.CommentId;
import com.daol.logbuffer.post.command.post.PostId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentDataRepository extends JpaRepository<CommentData, CommentId> {

    @Query("""
select new com.daol.logbuffer.comment.query.CommentResponse(
    c.id.value,
    c.content,
    c.state,
    new com.daol.logbuffer.member.CommentMemberResponse(
        c.authorId.value,
        m.name,
        pi.url
    ),
    c.createdDate,
    c.modifiedDate
)
from CommentData c
join Member m on c.authorId.value = m.id.value
join ProfileImage pi on m.profileImage.id.value = pi.id.value
where c.postId = :postId
order by c.createdDate asc
""")
    Page<CommentResponse> findAllByPostId(PostId postId, Pageable pageable);
}