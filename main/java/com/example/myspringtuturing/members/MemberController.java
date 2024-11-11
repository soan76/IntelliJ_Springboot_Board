package com.example.myspringtuturing.members;

import com.example.myspringtuturing.jwt.dto.TokenResponse;
import com.example.myspringtuturing.members.dto.BasicResponse;
import com.example.myspringtuturing.members.dto.JoinRequest;
import com.example.myspringtuturing.members.dto.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<BasicResponse> join(@RequestBody JoinRequest request) {
        return memberService.joinMember(request);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        return memberService.login(loginRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> reAccessToken(HttpServletRequest request) {
        return memberService.accessTokenRequest(request);
    }
}
