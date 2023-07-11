package com.sparta.blog.like.entity;

import com.sparta.blog.blog.entity.Blog;
import com.sparta.blog.comment.entity.Comment;
import com.sparta.blog.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class LikeBlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public LikeBlog(Blog blog, User user) {
        this.blog=blog;
        this.user=user;
    }
}
