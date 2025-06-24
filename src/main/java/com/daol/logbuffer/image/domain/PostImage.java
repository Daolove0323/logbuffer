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
public class PostImage extends Image {

    @Embedded
    private PostId postId;

    private PostImage(UploaderId uploaderId) {
        super(uploaderId);
    }

    public static PostImage create(UploaderId uploaderId) {
        return new PostImage(uploaderId);
    }

    public void changePostId(PostId postId) {
        this.postId = postId;
    }
}