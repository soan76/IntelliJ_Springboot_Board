package com.example.myspringtuturing.posts;

import com.example.myspringtuturing.members.Member;
import com.example.myspringtuturing.members.MemberRepository;
import com.example.myspringtuturing.members.dto.BasicResponse;
import com.example.myspringtuturing.posts.dto.PostListResponse;
import com.example.myspringtuturing.posts.dto.PostRequest;
import com.example.myspringtuturing.posts.dto.PostResponse;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    // 게시글 작성
    public ResponseEntity<BasicResponse> post(PostRequest postRequest) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName()).orElseThrow();

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .postResisterDate(LocalDateTime.now())
                .member(member)
                .build();

        postRepository.save(post);

        return ResponseEntity.ok(new BasicResponse("게시글 등록 성공"));
    }

    // 게시글 세부 내용 보기
    public ResponseEntity<PostResponse> postDetail(Long postId) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName()).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        PostResponse postResponse = PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .postMember(member.getUsername())
                .createDate(post.getPostResisterDate())
                .build();

        return ResponseEntity.ok(postResponse);
    }

    // 게시글 전체 리스트 가져오기
    public List<PostListResponse> allPost() {
        return postRepository.findAll().stream() // 데이터를 한 번에 하나씩 처리할 수 있으며, 필터링/매핑/정렬/집계 등 가능
                .map(post -> PostListResponse.builder() // 각 요소를 원하는 값으로 변환할 때 사용함
                        .title(post.getTitle())
                        .postMember(post.getMember().getUsername())
                        .createDate(post.getPostResisterDate())
                        .build())
                .collect(Collectors.toList()); // 연산 결과를 특정 컬렉션으로 변환하며, 여기서는 List형태로 변환
    }

    // 게시글 수정
    public ResponseEntity<BasicResponse> updatePost(Long postId, PostUpdate update) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(check(authentication, postId)) {
            Post post = postRepository.findById(postId).orElseThrow();
            post.update(update.getTitle(), update.getContent());
            postRepository.save(post);
            return ResponseEntity.ok(new BasicResponse("게시글을 수정하였습니다."));
        }

        return ResponseEntity.ok(new BasicResponse("게시글을 수정할 수 없습니다."));

    }

    // 게시글 삭제
    public ResponseEntity<BasicResponse> deletePost(Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(check(authentication, postId)) {
            postRepository.deleteById(postId);
            return ResponseEntity.ok(new BasicResponse("게시글 삭제 완료"));
        }

        return ResponseEntity.ok(new BasicResponse("게시글을 삭제할 수 없습니다."));
    }

    // 본인 확인 과정
    private boolean check(Authentication authentication, Long postId) {
        String username = authentication.getName();

        Member loginMember = memberRepository.findByUsername(username).orElseThrow();
        Post postMember = postRepository.findById(postId).orElseThrow();

        if(loginMember.getUsername().equals(postMember.getMember().getUsername())) {
            return true;
        } else {
            return false;
        }
    }
}
