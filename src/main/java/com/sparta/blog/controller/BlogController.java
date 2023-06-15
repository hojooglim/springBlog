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

    @GetMapping("/blog")
    public List<ResponseDto> getBlog(){
        return blogService.getBlog();
    }

    @GetMapping("/blog/{id}")
    public ResponseDto getBlog(@PathVariable Long id){
        return blogService.getBlog(id);
    }
    @PostMapping("/blog")
    public ResponseDto createBlog(@ModelAttribute RequestDto requestDto){
        return blogService.createBlog(requestDto);
    }
    @PutMapping("/blog/{id}")
    public ResponseDto updateBlog(@PathVariable Long id, RequestDto requestDto){
        return blogService.updateBlog(id,requestDto);
    }
    @DeleteMapping("/blog/{id}")
    @ResponseBody
    public String deleteBlog(@PathVariable Long id,@RequestParam String password){
        //비번도 보내야되는데...
        //성공했다는 표시 반환...
        return blogService.deleteBlog(id,password);
    }
}
