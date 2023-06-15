package com.sparta.blog.dto;


import com.sparta.blog.entity.Blog;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseDto {
    Long id;
    String postName;
    String userName;
    String contents;
    LocalDateTime createdAt;

    public ResponseDto(Blog blog) {
        this.id = blog.getId();
        this.postName = blog.getPostName();
        this.userName = blog.getUserName();
        this.contents = blog.getContents();
        this.createdAt = blog.getCreatedAt();
    }
}
