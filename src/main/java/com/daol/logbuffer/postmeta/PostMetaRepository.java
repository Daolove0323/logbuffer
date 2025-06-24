package com.daol.logbuffer.postmeta;

import com.daol.logbuffer.post.command.PostId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostMetaRepository extends JpaRepository<PostMeta, PostId> {

}