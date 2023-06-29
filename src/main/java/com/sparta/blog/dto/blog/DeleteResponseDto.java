package com.sparta.blog.dto.blog;

import lombok.Getter;

@Getter
public class DeleteResponseDto {

    private String msg;
    private int statusCode;

    public DeleteResponseDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }

}
