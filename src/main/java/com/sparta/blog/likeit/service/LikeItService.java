package com.sparta.blog.likeit.service;

import com.sparta.blog.blog.entity.Blog;
import com.sparta.blog.blog.repository.BlogRepository;
import com.sparta.blog.comment.entity.Comment;
import com.sparta.blog.comment.repository.CommentRepository;
import com.sparta.blog.likeit.entity.LikeIt;
import com.sparta.blog.likeit.repository.LikeItRepository;
import com.sparta.blog.security.filter.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeItService {
    private final LikeItRepository likeItRepository;
    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;

    public void blogLike(Long blogId, UserDetailsImpl userDetails) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(
                ()-> new NullPointerException("not found blog")
        );

        Optional<LikeIt> blogLike = likeItRepository.findByBlogAndUser(blog, userDetails.getUser());

        blogLike.ifPresentOrElse(
                likeIt -> { // 게시물과 유저를 통해 좋아요를 이미 누른게 확인이 되면 삭제
                    likeItRepository.delete(blogLike.get());
                },
                () -> { // 좋아요를 아직 누르지 않았을 땐 추가
                    likeItRepository.save(new LikeIt(blog, userDetails.getUser()));
                }
        );

    }


    public void commentLike(Long commentId, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new NullPointerException("not found blog")
        );

        Optional<LikeIt> commentLike = likeItRepository.findByCommentAndUser(comment, userDetails.getUser());

        commentLike.ifPresentOrElse(
                likeIt -> {
                    likeItRepository.delete(commentLike.get());
                },
                ()->{
                    likeItRepository.save(new LikeIt(comment,userDetails.getUser()));
                }
        );
    }
}
