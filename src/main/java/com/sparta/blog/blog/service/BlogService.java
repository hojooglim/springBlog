package com.sparta.blog.blog.service;

import com.sparta.blog.blog.dto.BlogRequestDto;
import com.sparta.blog.blog.dto.BlogResponseDto;
import com.sparta.blog.blog.entity.Blog;
import com.sparta.blog.security.filter.UserDetailsImpl;

import java.util.List;

public interface BlogService {
    Blog findBlog(Long id);

    BlogResponseDto getBlog(Long id);

    List<BlogResponseDto> getBlogs();

    BlogResponseDto createBlog(BlogRequestDto blogRequestDto, UserDetailsImpl userDetails);

    BlogResponseDto updateBlog(Long id, BlogRequestDto blogRequestDto, UserDetailsImpl userDetails);

    void deleteBlog(Long id, UserDetailsImpl userDetails);

    boolean authUser(Blog blog, UserDetailsImpl userDetails);
}
