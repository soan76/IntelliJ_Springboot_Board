package com.example.myspringtuturing.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class AccessTokenResponse {
    private String accessToken;
}
