package com.sparta.blog.comment.service;

import com.sparta.blog.blog.service.BlogServiceImpl;
import com.sparta.blog.comment.dto.CommentRequestDto;
import com.sparta.blog.comment.dto.CommentResponseDto;
import com.sparta.blog.comment.entity.Comment;
import com.sparta.blog.comment.repository.CommentRepository;
import com.sparta.blog.exception.AuthUserException;
import com.sparta.blog.security.filter.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final BlogServiceImpl blogService;

    @Override
    public CommentResponseDto createComment(Long blogId, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        return new CommentResponseDto(commentRepository.save(new Comment(requestDto, userDetails, blogService.findBlog(blogId))));
    }

    @Override
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto,UserDetailsImpl userDetails) {
        Comment comment = findComment(commentId);
        if(blogService.authUser(comment.getBlog(),userDetails)){
            comment.update(requestDto);
        }else {
            throw new AuthUserException("작성자만 삭제/수정할 수 있습니다.");
        }
        return new CommentResponseDto(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, UserDetailsImpl userDetails) {
        Comment comment = findComment(commentId);
        if(blogService.authUser(comment.getBlog(),userDetails)){
            commentRepository.delete(comment);
        }else {
            throw new AuthUserException("작성자만 삭제/수정할 수 있습니다.");
        }
    }

    @Override
    public Comment findComment(long commentId){
        return commentRepository.findById(commentId).orElseThrow(
                ()-> new NullPointerException("댓글이 존재하지 않습니다."));
    }

}
