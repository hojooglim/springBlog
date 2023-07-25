package com.sparta.blog.comment.controller;

import com.sparta.blog.comment.dto.CommentRequestDto;
import com.sparta.blog.comment.dto.CommentResponseDto;
import com.sparta.blog.security.filter.UserDetailsImpl;
import com.sparta.blog.comment.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;

    @PostMapping("/post/{blogId}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long blogId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(commentService.createComment(blogId,requestDto,userDetails), HttpStatus.OK);
    }

    @PutMapping("/post/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId,@RequestBody CommentRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(commentService.updateComment(commentId,requestDto,userDetails),HttpStatus.OK);
    }

    @DeleteMapping("/post/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.deleteComment(commentId,userDetails);
        return new ResponseEntity<>("댓글이 삭제되었습니다.",HttpStatus.OK);
    }

}
