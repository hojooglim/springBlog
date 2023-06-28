package com.sparta.blog.dto;

import com.sparta.blog.entity.User;
import lombok.Getter;

@Getter
public class SignResponseDto {
    private String username;
    private String email;

    public SignResponseDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
