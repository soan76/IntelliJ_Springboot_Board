package com.example.myspringtuturing.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class StatusResponse {
    private int code;
    private String message;
}
