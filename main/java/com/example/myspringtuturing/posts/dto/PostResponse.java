package com.example.myspringtuturing.posts.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class PostResponse {
    private String title;
    private String content;
    private String postMember;
    private LocalDateTime createDate;
}
