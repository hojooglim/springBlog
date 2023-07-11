package com.sparta.blog.blog.controller;

import com.sparta.blog.blog.dto.BlogRequestDto;
import com.sparta.blog.blog.dto.BlogResponseDto;
import com.sparta.blog.security.filter.UserDetailsImpl;
import com.sparta.blog.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/blogs")
    public List<BlogResponseDto> getBlogs(){
        return blogService.getBlogs();
    }

    @GetMapping("/blogs/{blog_id}")
    public BlogResponseDto getBlog(@PathVariable Long blog_id){
        return blogService.getBlog(blog_id);
    }

    @PostMapping("/blog")
    public ResponseEntity<BlogResponseDto> createBlog(@RequestBody BlogRequestDto blogRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        BlogResponseDto responseDto = blogService.createBlog(blogRequestDto,userDetails);
        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/blog/{blog_id}")
    public BlogResponseDto updateBlog(@PathVariable Long blog_id, @RequestBody BlogRequestDto blogRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return blogService.updateBlog(blog_id, blogRequestDto, userDetails);
    }

    @DeleteMapping("/blog/{blog_id}")
    public BlogResponseDto deleteBlog(@PathVariable Long blog_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return blogService.deleteBlog(blog_id, userDetails);
    }

    //get,post,put,delte -> nullex
    //put,delete -> illega
}
