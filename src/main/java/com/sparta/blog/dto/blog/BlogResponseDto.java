package com.sparta.blog.dto.blog;


import com.sparta.blog.dto.comment.CommentResponseDto;
import com.sparta.blog.entity.Blog;
import com.sparta.blog.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BlogResponseDto {

    Long id;
    String postName;
    String userName;
    String contents;
    LocalDateTime createdAt;
    private List<CommentResponseDto> commentList;

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.postName = blog.getPostName();
        this.userName = blog.getUserName();
        this.contents = blog.getContents();
        this.createdAt = blog.getCreatedAt();
        this.commentList = new ArrayList<>();
        for (Comment comment : blog.getCommentList()) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            this.commentList.add(commentResponseDto);
        }
    }

}
