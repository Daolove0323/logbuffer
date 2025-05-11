package com.daol.logbuffer.image.domain.postimage;

import com.daol.logbuffer.image.domain.Image;
import com.daol.logbuffer.image.domain.UploaderId;
import com.daol.logbuffer.post.command.PostId;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage extends Image {

    @EmbeddedId
    PostImageId id;

    @Embedded
    private PostId postId;

    private PostImage(UploaderId uploaderId) {
        super(uploaderId);
        this.id = PostImageId.generate();
    }

    public static PostImage create(UploaderId uploaderId) {
        return new PostImage(uploaderId);
    }

    public void setPostId(PostId postId) {
        this.postId = postId;
    }
}