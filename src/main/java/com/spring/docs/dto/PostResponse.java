package com.spring.docs.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {

    private final String title;
    private final String content;

    @Builder
    public PostResponse(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
