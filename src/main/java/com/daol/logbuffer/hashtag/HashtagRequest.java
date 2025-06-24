package com.daol.logbuffer.hashtag;

import jakarta.validation.constraints.NotEmpty;

public record HashtagRequest(
    
    @NotEmpty
    String name
) {

}