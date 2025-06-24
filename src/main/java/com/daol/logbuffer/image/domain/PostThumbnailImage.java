package com.daol.logbuffer.image.domain;

import com.daol.logbuffer.post.command.PostId;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostThumbnailImage extends Image {

    @Embedded
    private PostId postId;

    private PostThumbnailImage(UploaderId uploaderId) {
        super(uploaderId);
    }

    public static PostThumbnailImage create(UploaderId uploaderId) {
        return new PostThumbnailImage(uploaderId);
    }

    public void changePostId(PostId postId) {
        this.postId = postId;
    }
}