package com.daol.logbuffer.comment.comment.application;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CommentUpdateRequest(

    @NotNull(message = "댓글 내용은 필수입니다.")
    @Length(min = 10, max = 500, message = "댓글은 10자 이상 500자 이하로 작성해야 합니다.")
    String content,

    @NotNull
    Boolean isHidden
) {

}