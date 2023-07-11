package com.sparta.blog.like.repository;

import com.sparta.blog.blog.entity.Blog;
import com.sparta.blog.like.entity.LikeBlog;
import com.sparta.blog.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeBlog, Long> {
    Optional<LikeBlog> findByBlogAndUser(Blog blog, User user);

}
