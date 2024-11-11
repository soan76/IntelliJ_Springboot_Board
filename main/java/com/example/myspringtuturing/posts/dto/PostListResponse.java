package com.example.myspringtuturing.posts.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostListResponse {
    private String title;
    private String postMember;
    private LocalDateTime createDate;
}
