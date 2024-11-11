package com.example.myspringtuturing.replies;

import com.example.myspringtuturing.members.Member;
import com.example.myspringtuturing.members.MemberRepository;
import com.example.myspringtuturing.members.dto.BasicResponse;
import com.example.myspringtuturing.posts.Post;
import com.example.myspringtuturing.posts.PostRepository;
import com.example.myspringtuturing.replies.dto.ReplyList;
import com.example.myspringtuturing.replies.dto.ReplyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;

    public ResponseEntity<BasicResponse> reply(Long postId, ReplyRequest replyRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Member member = memberRepository.findByUsername(authentication.getName()).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        Reply reply = Reply.builder()
                .member(member)
                .post(post)
                .commentResisterDate(LocalDateTime.now())
                .content(replyRequest.getContent())
                .build();

        replyRepository.save(reply);

        return ResponseEntity.ok(new BasicResponse("댓글 등록 성공"));
    }

    public List<ReplyList> allReplies(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        List<Reply> replies = replyRepository.findByPost(post);

        return replies.stream()
                .map(reply ->ReplyList.builder()
                        .content(reply.getContent())
                        .replyMember(reply.getMember().getUsername())
                        .createDate(reply.getCommentResisterDate())
                        .build())
                .collect(Collectors.toList()); }

    public ResponseEntity<BasicResponse> updateReply(Long replyId, ReplyRequest update) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(check(authentication, replyId)) {
            Reply reply = replyRepository.findById(replyId).orElseThrow();
            reply.update(update.getContent());
            replyRepository.save(reply);
            return ResponseEntity.ok(new BasicResponse("댓글을 수정하였습니다."));
        }

        return ResponseEntity.ok(new BasicResponse("댓글을 수정할 수 없습니다."));

    }

    public ResponseEntity<BasicResponse> deleteReply(Long replyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(check(authentication, replyId)) {
            replyRepository.deleteById(replyId);
            return ResponseEntity.ok(new BasicResponse("댓글 삭제 완료"));
        }

        return ResponseEntity.ok(new BasicResponse("댓글을 삭제할 수 없습니다."));
    }

    // 본인 확인 과정
    private boolean check(Authentication authentication, Long replyId) {
        String username = authentication.getName();

        Member loginMember = memberRepository.findByUsername(username).orElseThrow();
        Reply replyMember = replyRepository.findById(replyId).orElseThrow();

        if(loginMember.getUsername().equals(replyMember.getMember().getUsername())) {
            return true;
        } else {
            return false;
        }
    }
}
