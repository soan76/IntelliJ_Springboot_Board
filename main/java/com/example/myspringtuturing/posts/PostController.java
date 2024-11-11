package com.example.myspringtuturing.posts;

import com.example.myspringtuturing.members.dto.BasicResponse;
import com.example.myspringtuturing.posts.dto.PostListResponse;
import com.example.myspringtuturing.posts.dto.PostRequest;
import com.example.myspringtuturing.posts.dto.PostResponse;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @PostMapping("")
    public ResponseEntity<BasicResponse> post(@RequestBody PostRequest postRequest) {
        return postService.post(postRequest);
    }

    // 게시글 세부 내용 보기
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> postDetail(@PathVariable Long postId) {
        return postService.postDetail(postId);
    }

    // 게시글 전체 리스트 가져오기
    @GetMapping("/all")
    public ResponseEntity<List<PostListResponse>> allPost() {
        List<PostListResponse> postListResponses = postService.allPost();
        return ResponseEntity.ok(postListResponses);
    }

    // 게시글 수정
    @PutMapping("/update/{postId}")
    public ResponseEntity<BasicResponse> updatePost(@PathVariable Long postId, @RequestBody PostUpdate postUpdate) {
        return postService.updatePost(postId, postUpdate);
    }

    // 게시글 삭제
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<BasicResponse> deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }




}
