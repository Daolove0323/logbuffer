package com.daol.logbuffer._common.event;

import com.daol.logbuffer.category.CategoryId;
import com.daol.logbuffer.post.command.PostId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDeletedEvent extends Event {

    private final PostId postId;
    private final CategoryId categoryId;
}