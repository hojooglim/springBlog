package com.sparta.blog.user.dto;

import lombok.Getter;

@Getter
public class SignResponseDto {

    private String msg;
    private int statusCode;

    public SignResponseDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
