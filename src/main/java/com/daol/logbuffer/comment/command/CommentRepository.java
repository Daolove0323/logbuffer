package com.daol.logbuffer.comment.command;

import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, CommentId> {

}