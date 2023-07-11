package com.sparta.blog.blog.dto;


import com.sparta.blog.comment.dto.CommentResponseDto;
import com.sparta.blog.blog.entity.Blog;
import com.sparta.blog.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class BlogResponseDto {

    Long id;
    String title;
    String userName;
    String contents;
    LocalDateTime createdAt;
    private List<CommentResponseDto> commentList;

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.userName = blog.getUserName();
        this.contents = blog.getContents();
        this.createdAt = blog.getCreatedAt();
        this.commentList = new ArrayList<>();
        for (Comment comment : blog.getCommentList()) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            this.commentList.add(commentResponseDto);
            Collections.reverse(this.commentList);
        }

    }

}
