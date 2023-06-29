package com.sparta.blog.repository;

import com.sparta.blog.entity.Blog;
import com.sparta.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog,Long> {
    List<Blog> findAllByOrderByModifiedAtDesc();

    List<Blog> findByUser(User user);

}
