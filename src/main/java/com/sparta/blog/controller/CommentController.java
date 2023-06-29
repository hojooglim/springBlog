package com.sparta.blog.controller;

import com.sparta.blog.dto.comment.CommentRequestDto;
import com.sparta.blog.dto.comment.CommentResponseDto;
import com.sparta.blog.entity.User;
import com.sparta.blog.filter.UserDetailsImpl;
import com.sparta.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/post")
    public CommentResponseDto createComment(@ModelAttribute CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createComment(requestDto,userDetails);
    }

    @PutMapping("/post/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id,@ModelAttribute CommentRequestDto requestDto){
        return commentService.updateComment(id,requestDto);
    }

    @DeleteMapping("/post/{id}")
    public void deleteComment(@PathVariable Long id){
      commentService.deleteComment(id);
    }

}
