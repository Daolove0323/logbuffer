package com.daol.logbuffer.comment.command;

import com.daol.logbuffer._common.event.CommentCreatedEvent;
import com.daol.logbuffer._common.event.Events;
import com.daol.logbuffer._common.exception.EntityNotFoundException;
import com.daol.logbuffer.post.command.PostId;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @EmbeddedId
    private CommentId id;

    @Column(name = "content", length = 500)
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

    protected Comment() {
        this.id = CommentId.generate();
    }

    private Comment(String content, PostId postId, CommentAuthorId authorId) {
        this.id = CommentId.generate();
        this.content = content;
        this.postId = postId;
        this.authorId = authorId;
    }

    public static Comment create(String content, PostId postId, CommentAuthorId authorId, boolean isHidden) {
        Comment comment = new Comment(content, postId, authorId);
        if (isHidden) {
            comment.hide();
        } else {
            comment.publish();
        }
        Events.raise(new CommentCreatedEvent(postId));
        return comment;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void hide() {
        this.state = CommentState.HIDDEN;
    }

    public void publish() {
        this.state = CommentState.PUBLISHED;
    }

    public void verifyAuthor(CommentAuthorId authorId) {
        if (authorId == null) {
            throw new EntityNotFoundException("댓글 작성자 ID가 존재하지 않습니다.");
        }
        if (!this.getAuthorId().equals(authorId)) {
            throw new EntityNotFoundException("댓글 작성자가 일치하지 않습니다.");
        }
    }
}