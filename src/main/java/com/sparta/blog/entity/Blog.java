package com.sparta.blog.entity;

import com.sparta.blog.dto.blog.BlogRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    String postName;

    @Column (name = "userName")
    String userName;

    @Column (name = "contents", length = 1000)
    String contents;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private  User user;

    public Blog(BlogRequestDto blogRequestDto, User user) {
        this.postName = blogRequestDto.getPostName();
        this.userName = user.getUsername();
        this.contents = blogRequestDto.getContents();
        this.user = user;
    }

    public Blog update(BlogRequestDto blogRequestDto) {
        this.postName = blogRequestDto.getPostName();
        this.contents = blogRequestDto.getContents();
        return new Blog();
    }
}
