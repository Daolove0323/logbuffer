package com.daol.logbuffer.post.command;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, PostId> {

}