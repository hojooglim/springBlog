package com.sparta.blog.dto;

import com.sparta.blog.entity.User;
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
