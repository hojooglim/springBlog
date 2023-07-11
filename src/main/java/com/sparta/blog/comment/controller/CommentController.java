package com.sparta.blog.comment.controller;

import com.sparta.blog.comment.dto.CommentRequestDto;
import com.sparta.blog.comment.dto.CommentResponseDto;
import com.sparta.blog.security.filter.UserDetailsImpl;
import com.sparta.blog.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/post/{blog_id}")
    public CommentResponseDto createComment(@PathVariable Long blog_id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createComment(blog_id,requestDto,userDetails);
    }

    @PutMapping("/post/{comment_id}")
    public CommentResponseDto updateComment(@PathVariable Long comment_id,@RequestBody CommentRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment(comment_id,requestDto,userDetails);
    }

    @DeleteMapping("/post/{comment_id}")
    public CommentResponseDto deleteComment(@PathVariable Long comment_id,@AuthenticationPrincipal UserDetailsImpl userDetails){
      return commentService.deleteComment(comment_id,userDetails);
    }
    //put,delet null,ill ex
}
