package com.sparta.blog.entity;

import com.sparta.blog.dto.RequestDto;
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

    @Column (name = "password", nullable = true)
    String password;

    public Blog(RequestDto requestDto) {
        this.postName = requestDto.getPostName();
        this.userName = requestDto.getUserName();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
    }

    public void update(RequestDto requestDto) {
        this.postName = requestDto.getPostName();
        this.userName = requestDto.getUserName();
        this.contents = requestDto.getContents();
    }
}
