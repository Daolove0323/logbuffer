package com.daol.logbuffer.postmeta;

import com.daol.logbuffer.post.command.PostId;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import java.util.List;

@Entity
public class PostMeta {

    @EmbeddedId
    private PostId id;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "comment_count")
    private Integer commentCount;

    // Todo: hashtag를 String이 아닌 별도의 밸류 타입을 사용할지 고민
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

    public void incrementCommentCount() {
        commentCount++;
    }

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