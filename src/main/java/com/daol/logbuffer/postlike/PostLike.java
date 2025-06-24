package com.daol.logbuffer.postlike;

import com.daol.logbuffer.post.command.PostId;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class PostLike {

    @EmbeddedId
    private PostLikeId id;

    @Embedded
    private PostId postId;

    @Embedded
    private PostLikerId likerId;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    protected PostLike() {
        this.id = PostLikeId.generate();
    }

    private PostLike(PostId postId, PostLikerId likerId) {
        this.id = PostLikeId.generate();
        this.postId = postId;
        this.likerId = likerId;
    }

    public static PostLike create(PostId postId, PostLikerId likerId) {
        return new PostLike(postId, likerId);
    }
}