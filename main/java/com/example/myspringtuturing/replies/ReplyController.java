package com.example.myspringtuturing.replies;

import com.example.myspringtuturing.members.dto.BasicResponse;
import com.example.myspringtuturing.replies.dto.ReplyList;
import com.example.myspringtuturing.replies.dto.ReplyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    // 댓글 작성
    @PostMapping("/{postId}")
    public ResponseEntity<BasicResponse> reply(@PathVariable Long postId,
                                               @RequestBody ReplyRequest replyRequest) {
        return replyService.reply(postId, replyRequest);
    }

    // 게시글 댓글 가져오기
    @GetMapping("/{postId}")
    public ResponseEntity<List<ReplyList>> allReply(@PathVariable Long postId) {
        List<ReplyList> replytLists = replyService.allReplies(postId);
        return ResponseEntity.ok(replytLists);
    }

    // 댓글 수정
    @PutMapping("/update/{replyId}")
    public ResponseEntity<BasicResponse> updateReply(@PathVariable Long replyId,
                                                     @RequestBody ReplyRequest replyRequest) {
        return replyService.updateReply(replyId, replyRequest);
    }

    // 댓글 삭제
    @DeleteMapping("/delete/{replyId}")
    public ResponseEntity<BasicResponse> deleteReply(@PathVariable Long replyId) {
        return replyService.deleteReply(replyId);
    }
}
