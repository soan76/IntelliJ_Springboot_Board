package com.example.myspringtuturing.members.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginRequest {
    private String username;
    private String password;
}
