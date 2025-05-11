package com.daol.logbuffer.member;

import java.util.UUID;

public record PostMemberResponse(
    UUID id,
    String name,
    String profileImageUrl
) {

}