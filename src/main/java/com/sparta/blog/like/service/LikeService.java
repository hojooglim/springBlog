package com.sparta.blog.like.service;

import com.sparta.blog.blog.entity.Blog;
import com.sparta.blog.blog.repository.BlogRepository;
import com.sparta.blog.like.entity.LikeBlog;
import com.sparta.blog.like.repository.LikeRepository;
import com.sparta.blog.security.filter.UserDetailsImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {
    private LikeRepository likeRepository;
    private BlogRepository blogRepository;

    public void blogLike(Long blogId, UserDetailsImpl userDetails) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(
                ()-> new NullPointerException("not found blog")
        );

        Optional<LikeBlog> blogLike = likeRepository.findByBlogAndUser(blog, userDetails.getUser());

        blogLike.ifPresentOrElse(
                like -> { // 게시물과 유저를 통해 좋아요를 이미 누른게 확인이 되면 삭제
                    likeRepository.delete(like);
                },
                () -> { // 좋아요를 아직 누르지 않았을 땐 추가
                    likeRepository.save(new LikeBlog(blog, userDetails.getUser()));
                }
        );

    }


}
