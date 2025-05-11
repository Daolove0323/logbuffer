package com.daol.logbuffer.comment.query;

import com.daol.logbuffer.comment.command.CommentAuthorId;
import com.daol.logbuffer.comment.command.CommentId;
import com.daol.logbuffer.comment.command.CommentState;
import com.daol.logbuffer.post.command.PostId;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Table(name = "comment")
@EntityListeners(AuditingEntityListener.class)
public class CommentData {

    @EmbeddedId
    private CommentId id;

    @Column(name = "content")
    private String content;

    @Embedded
    private PostId postId;

    @Embedded
    private CommentAuthorId authorId;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private CommentState state;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}