package com.sparta.blog.blog.controller;

import com.sparta.blog.blog.dto.BlogRequestDto;
import com.sparta.blog.blog.dto.BlogResponseDto;
import com.sparta.blog.blog.dto.BlogDeleteResponseDto;
import com.sparta.blog.security.filter.UserDetailsImpl;
import com.sparta.blog.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/blogs")
    public ResponseEntity<List<BlogResponseDto>> getBlogs(){
        return new ResponseEntity<>(blogService.getBlogs(),HttpStatus.OK);
    }

    @GetMapping("/blogs/{blog_id}")
    public ResponseEntity<BlogResponseDto> getBlog(@PathVariable Long blog_id){
        return new ResponseEntity<>(blogService.getBlog(blog_id), HttpStatus.OK);
    }

    @PostMapping("/blog")
    public ResponseEntity<BlogResponseDto> createBlog(@RequestBody BlogRequestDto blogRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(blogService.createBlog(blogRequestDto,userDetails), HttpStatus.OK);
    }

    @PutMapping("/blog/{blog_id}")
    public ResponseEntity<BlogResponseDto> updateBlog(@PathVariable Long blog_id, @RequestBody BlogRequestDto blogRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(blogService.updateBlog(blog_id, blogRequestDto, userDetails),HttpStatus.OK);
    }

    @DeleteMapping("/blog/{blog_id}")
    public ResponseEntity<BlogDeleteResponseDto> deleteBlog(@PathVariable Long blog_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        blogService.deleteBlog(blog_id, userDetails);
        return new ResponseEntity<>(new BlogDeleteResponseDto("삭제가 완료되었습니다.",200),HttpStatus.OK);
    }



    //get,post,put,delte -> nullex
    //put,delete -> illega

}
