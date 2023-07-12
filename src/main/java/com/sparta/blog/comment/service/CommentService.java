package com.sparta.blog.comment.service;

import com.sparta.blog.blog.entity.Blog;
import com.sparta.blog.blog.repository.BlogRepository;
import com.sparta.blog.comment.dto.CommentRequestDto;
import com.sparta.blog.comment.dto.CommentResponseDto;
import com.sparta.blog.comment.entity.Comment;
import com.sparta.blog.security.filter.UserDetailsImpl;
import com.sparta.blog.comment.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;

    public CommentResponseDto createComment(Long blog_id, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        Blog blog = blogRepository.findById(blog_id).orElseThrow(
                ()-> new NullPointerException("글이 존재하지 않습니다.")
        );
        return new CommentResponseDto(commentRepository.save(new Comment(requestDto, userDetails, blog)));
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto,UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new NullPointerException("작성한 댓글이 없습니다.")
        );
        if(comment.getId().equals(userDetails.getUser().getId())){
            comment.update(requestDto);
        }else {
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }
        return new CommentResponseDto(comment);
    }

    public CommentResponseDto deleteComment(Long id, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new NullPointerException("댓글이 존재하지 않습니다.")
        );
        if(comment.getId().equals(userDetails.getUser().getId())){
            commentRepository.delete(comment);
        }else {
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }
        return new CommentResponseDto(comment);
    }
}
