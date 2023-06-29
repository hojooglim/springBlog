package com.sparta.blog.controller;

import com.sparta.blog.dto.blog.BlogRequestDto;
import com.sparta.blog.dto.blog.BlogResponseDto;
import com.sparta.blog.dto.login.LoginResponseDto;
import com.sparta.blog.filter.UserDetailsImpl;
import com.sparta.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/blogs")
    public List<BlogResponseDto> getBlogs(){
        return blogService.getBlogs();
    }//인증 x

    @GetMapping("/blogs/{id}")
    public BlogResponseDto getBlog(@PathVariable Long id){
        return blogService.getBlog(id);
    }//인증 x

    @PostMapping("/blog")
    public BlogResponseDto createBlog(@ModelAttribute BlogRequestDto blogRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return blogService.createBlog(blogRequestDto,userDetails.getUser());
    }

    @PutMapping("/blog/{id}")
    public BlogResponseDto updateBlog(@PathVariable Long id, @ModelAttribute BlogRequestDto blogRequestDto){
        return blogService.updateBlog(id, blogRequestDto);
    }

    @DeleteMapping("/blog/{id}")
    public LoginResponseDto deleteBlog(@PathVariable Long id){
        return blogService.deleteBlog(id);
    }
}
