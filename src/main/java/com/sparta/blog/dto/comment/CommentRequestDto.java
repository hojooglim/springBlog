package com.sparta.blog.dto.comment;

import com.sparta.blog.entity.User;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    private String username;
    private String comment;

    public CommentRequestDto(User user, String comment) {
        this.username = user.getUsername();
        this.comment = comment;
    }
}
