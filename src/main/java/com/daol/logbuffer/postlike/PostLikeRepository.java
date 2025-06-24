package com.daol.logbuffer.postlike;

import com.daol.logbuffer.post.command.PostId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {

    Optional<PostLike> findByPostIdAndLikerId(PostId postId, PostLikerId likerId);
}