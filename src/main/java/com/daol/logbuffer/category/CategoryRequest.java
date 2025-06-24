package com.daol.logbuffer.category;

import jakarta.validation.constraints.NotEmpty;

public record CategoryRequest(

    @NotEmpty
    String name
) {

}