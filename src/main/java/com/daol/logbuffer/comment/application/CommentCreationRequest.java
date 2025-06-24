package com.daol.logbuffer.comment.application;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CommentCreationRequest(

    @NotNull
    @Length(min = 10, max = 500)
    String content,

    @NotNull
    Boolean isHidden
) {

}