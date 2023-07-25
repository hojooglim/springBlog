package com.sparta.blog.comment.service;

import com.sparta.blog.comment.dto.CommentRequestDto;
import com.sparta.blog.comment.dto.CommentResponseDto;
import com.sparta.blog.comment.entity.Comment;
import com.sparta.blog.security.filter.UserDetailsImpl;

public interface CommentService {

    CommentResponseDto createComment(Long blogId, CommentRequestDto requestDto, UserDetailsImpl userDetails);

    CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto,UserDetailsImpl userDetails);

    void deleteComment(Long commentId, UserDetailsImpl userDetails);

    Comment findComment(long commentId);
}
