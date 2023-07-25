package com.sparta.blog.blog.controller;

import com.sparta.blog.blog.dto.BlogRequestDto;
import com.sparta.blog.blog.dto.BlogResponseDto;
import com.sparta.blog.security.filter.UserDetailsImpl;
import com.sparta.blog.blog.service.BlogServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogServiceImpl blogService;

    @GetMapping("/blogs")
    public ResponseEntity<List<BlogResponseDto>> getBlogs(){
        return new ResponseEntity<>(blogService.getBlogs(),HttpStatus.OK);
    }

    @GetMapping("/blogs/{blogId}")
    public ResponseEntity<BlogResponseDto> getBlog(@PathVariable Long blogId){
        return new ResponseEntity<>(blogService.getBlog(blogId), HttpStatus.OK);
    }

    @PostMapping("/blog")
    public ResponseEntity<BlogResponseDto> createBlog(@RequestBody BlogRequestDto blogRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(blogService.createBlog(blogRequestDto,userDetails), HttpStatus.OK);
    }

    @PutMapping("/blog/{blogId}")
    public ResponseEntity<BlogResponseDto> updateBlog(@PathVariable Long blogId, @RequestBody BlogRequestDto blogRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(blogService.updateBlog(blogId, blogRequestDto, userDetails),HttpStatus.OK);
    }

    @DeleteMapping("/blog/{blogId}")
    public ResponseEntity<String> deleteBlog(@PathVariable Long blogId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        blogService.deleteBlog(blogId, userDetails);
        return new ResponseEntity<>("삭제가 완료되었습니다.", HttpStatus.OK);
    }

}
