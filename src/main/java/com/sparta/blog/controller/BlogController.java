package com.sparta.blog.controller;

import com.sparta.blog.dto.RequestDto;
import com.sparta.blog.dto.ResponseDto;
import com.sparta.blog.service.BlogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BlogController {
    BlogService blogService;
    public BlogController(BlogService blogService){
        this.blogService = blogService;
    }

    @GetMapping("/blogs")
    public List<ResponseDto> getBlogs(){
        return blogService.getBlogs();
    }

    @GetMapping("/blogs/{id}")
    public ResponseDto getBlog(@PathVariable Long id){
        return blogService.getBlog(id);
    }
    @PostMapping("/blogs")
    public ResponseDto createBlog(@ModelAttribute RequestDto requestDto){
        return blogService.createBlog(requestDto);
    }
    @PutMapping("/blogs/{id}")
    public ResponseDto updateBlog(@PathVariable Long id,@ModelAttribute RequestDto requestDto){
        return blogService.updateBlog(id,requestDto);
    }
    @DeleteMapping("/blogs/{id}")
    @ResponseBody
    public String deleteBlog(@PathVariable Long id,@ModelAttribute RequestDto requestDto){
        return blogService.deleteBlog(id,requestDto);
    }
}
