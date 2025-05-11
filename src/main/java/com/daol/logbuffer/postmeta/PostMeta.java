package com.daol.logbuffer.postmeta;

import com.daol.logbuffer.comment.command.CommentCreatedEvent;
import com.daol.logbuffer.comment.command.CommentDeletedEvent;
import com.daol.logbuffer.post.command.PostId;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.context.event.EventListener;

@Entity
public class PostMeta {

    @EmbeddedId
    private PostId id;

    @Column(name = "like_count")
    @NotNull
    private Integer likeCount;

    @Column(name = "comment_count")
    @NotNull
    private Integer commentCount;

    @Column(name = "hashtags")
    @Convert(converter = HashtagConverter.class)
    private List<String> hashtags;

    protected PostMeta() {
    }

    public static PostMeta create(List<String> hashtags) {
        PostMeta postMeta = new PostMeta();
        postMeta.id = PostId.generate();
        postMeta.likeCount = 0;
        postMeta.commentCount = 0;
        postMeta.hashtags = hashtags;
        return postMeta;
    }

    @EventListener(CommentCreatedEvent.class)
    public void incrementCommentCount() {
        commentCount++;
    }

    @EventListener(CommentDeletedEvent.class)
    public void decrementCommentCount() {
        if (this.commentCount <= 0) {
            throw new RuntimeException();
        }
        commentCount--;
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        if (this.likeCount <= 0) {
            throw new RuntimeException();
        }
        this.likeCount--;
    }

    public void changeHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }
}