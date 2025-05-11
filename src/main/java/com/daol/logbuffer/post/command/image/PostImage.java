package com.daol.logbuffer.post.command.image;

import com.daol.logbuffer.image.Image;
import com.daol.logbuffer.image.UploaderId;
import com.daol.logbuffer.post.command.post.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage extends Image {

    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public PostImage(String name, UploaderId uploaderId) {
        super(name, uploaderId);
    }

    public void setPost(Post post) {
        this.post = post;
    }
}