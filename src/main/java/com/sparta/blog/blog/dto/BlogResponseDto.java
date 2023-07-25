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

    private final Long id;
    private final String title;
    private final String userName;
    private final String contents;
    private final LocalDateTime createdAt;
    private final int LikeItBlogCount;
    private final List<CommentResponseDto> commentList;


    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.userName = blog.getUserName();
        this.contents = blog.getContents();
        this.createdAt = blog.getCreatedAt();
        this.LikeItBlogCount=blog.getLikeItList().size();
        this.commentList = new ArrayList<>();
        for (Comment comment : blog.getCommentList()) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            this.commentList.add(commentResponseDto);
            Collections.reverse(this.commentList);
        }

    }

}
