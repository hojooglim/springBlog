package com.sparta.blog.likeit.entity;

import com.sparta.blog.blog.entity.Blog;
import com.sparta.blog.comment.entity.Comment;
import com.sparta.blog.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class LikeIt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public LikeIt(Blog blog, User user) {
        this.blog=blog;
        this.user=user;
    }
    public LikeIt(Comment comment, User user) {
        this.comment=comment;
        this.user=user;
    }
}
