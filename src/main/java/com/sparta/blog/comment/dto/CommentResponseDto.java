package com.sparta.blog.comment.dto;

import com.sparta.blog.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private String comment;
    private int LikeItCommentCount;

    public CommentResponseDto(Comment comment) {
        this.comment=comment.getComment();
        this.LikeItCommentCount=comment.getLikeItList().size();
    }
}
