package com.example.myspringtuturing.replies.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReplyList {
    private String content;
    private String replyMember;
    private LocalDateTime createDate;
}
