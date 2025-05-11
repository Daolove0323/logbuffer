package com.daol.logbuffer.image.domain.postthumbnailimage;

import com.daol.logbuffer.image.domain.UploaderId;
import com.daol.logbuffer.image.domain.Image;
import com.daol.logbuffer.image.domain.postimage.PostImage;
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
public class PostThumbnailImage extends Image {

    @EmbeddedId
    private PostThumbnailImageId id;

    @Embedded
    private PostId postId;

    public PostThumbnailImage(UploaderId uploaderId) {
        super(uploaderId);
        this.id = PostThumbnailImageId.generate();
    }

    public static PostThumbnailImage create(UploaderId uploaderId) {
        return new PostThumbnailImage(uploaderId);
    }

    public void setPostId(PostId postId) {
        this.postId = postId;
    }
}