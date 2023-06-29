package com.sparta.blog.dto.comment;

import com.sparta.blog.entity.Comment;

public class CommentResponseDto {

    private String comment;

    public CommentResponseDto(Comment comment) {

        this.comment=comment.getComment();

    }
}
