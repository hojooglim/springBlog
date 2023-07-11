package com.sparta.blog.likeit.repository;

import com.sparta.blog.blog.entity.Blog;
import com.sparta.blog.comment.entity.Comment;
import com.sparta.blog.likeit.entity.LikeIt;
import com.sparta.blog.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeItRepository extends JpaRepository<LikeIt, Long> {
    Optional<LikeIt> findByBlogAndUser(Blog blog, User user);

    Optional<LikeIt> findByCommentAndUser(Comment comment, User user);


}
