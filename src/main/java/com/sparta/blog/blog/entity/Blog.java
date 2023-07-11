package com.sparta.blog.blog.entity;

import com.sparta.blog.blog.dto.BlogRequestDto;
import com.sparta.blog.comment.entity.Comment;
import com.sparta.blog.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table (name = "blog")
public class Blog extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column (name = "postName")
    String title;

    @Column (name = "userName")
    String userName;

    @Column (name = "contents", length = 1000)
    String contents;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> commentList = new ArrayList<>();

    public Blog(BlogRequestDto blogRequestDto, User user) {
        this.title = blogRequestDto.getTitle();
        this.userName = user.getUsername();
        this.contents = blogRequestDto.getContents();
        this.user = user;
    }

    public void update(BlogRequestDto blogRequestDto) {
        this.title = blogRequestDto.getTitle();
        this.contents = blogRequestDto.getContents();
    }

}
