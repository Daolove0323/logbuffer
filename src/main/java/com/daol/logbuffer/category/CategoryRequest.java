package com.daol.logbuffer.category;

import jakarta.validation.constraints.NotEmpty;

public record CategoryRequest(

    @NotEmpty(message = "카테고리 이름은 비어있을 수 없습니다.")
    String name
) {

}