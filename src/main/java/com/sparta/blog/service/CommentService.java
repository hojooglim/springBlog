package com.sparta.blog.service;

import com.sparta.blog.dto.comment.CommentRequestDto;
import com.sparta.blog.dto.comment.CommentResponseDto;
import com.sparta.blog.entity.Comment;
import com.sparta.blog.filter.UserDetailsImpl;
import com.sparta.blog.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentResponseDto createComment(CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        Comment comment = new Comment(requestDto, userDetails);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new NullPointerException("작성한 댓글이 없습니다.")
        );
        comment.update(requestDto);

        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new NullPointerException("작성한 댓글이 없습니다.")
        );
        commentRepository.delete(comment);
    }
}
