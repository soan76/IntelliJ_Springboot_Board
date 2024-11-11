package com.example.myspringtuturing.replies;

import com.example.myspringtuturing.members.Member;
import com.example.myspringtuturing.posts.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Reply {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long commentId;

    private String content;


    // 작성자 조인
    @ManyToOne
    @JoinColumn(name = "userId")
    private Member member;

    // 게시글 조인
    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    @CreatedDate
    private LocalDateTime commentResisterDate;

    public void update(String content) {
        this.content = content;
    }
}
