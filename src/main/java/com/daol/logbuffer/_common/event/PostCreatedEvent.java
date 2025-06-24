package com.daol.logbuffer._common.event;

import com.daol.logbuffer.hashtag.HashtagId;
import com.daol.logbuffer.post.command.PostId;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCreatedEvent extends Event {

    private final PostId postId;
    private final List<HashtagId> hashtagIds;
}