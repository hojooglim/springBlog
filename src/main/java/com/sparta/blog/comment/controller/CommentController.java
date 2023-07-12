package com.sparta.blog.comment.controller;

import com.sparta.blog.comment.dto.CommentDeleteResponseDto;
import com.sparta.blog.comment.dto.CommentRequestDto;
import com.sparta.blog.comment.dto.CommentResponseDto;
import com.sparta.blog.security.filter.UserDetailsImpl;
import com.sparta.blog.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/post/{blog_id}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long blog_id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(commentService.createComment(blog_id,requestDto,userDetails), HttpStatus.OK);
    }

    @PutMapping("/post/{comment_id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long comment_id,@RequestBody CommentRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(commentService.updateComment(comment_id,requestDto,userDetails),HttpStatus.OK);
    }

    @DeleteMapping("/post/{comment_id}")
    public ResponseEntity<CommentDeleteResponseDto> deleteComment(@PathVariable Long comment_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.deleteComment(comment_id,userDetails);
        return new ResponseEntity<>(new CommentDeleteResponseDto("댓글이 삭제되었습니다.",200),HttpStatus.OK);
    }

}
