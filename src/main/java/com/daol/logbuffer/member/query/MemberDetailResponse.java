package com.daol.logbuffer.member.query;

import com.daol.logbuffer.member.common.Grade;
import java.time.LocalDateTime;
import java.util.UUID;

public record MemberDetailResponse(UUID memberId,
                                   String email,
                                   String name,
                                   Grade grade,
                                   String profileImageUrl,
                                   LocalDateTime createdDate) {

}