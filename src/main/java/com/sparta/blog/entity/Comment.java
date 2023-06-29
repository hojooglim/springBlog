package com.sparta.blog.entity;

import com.sparta.blog.dto.comment.CommentRequestDto;
import com.sparta.blog.filter.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    private String Comment;

    @Column
    private  String username;
    public Comment(CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        this.Comment = requestDto.getComment();
        this.username = userDetails.getUsername();

    }

    public void update(CommentRequestDto requestDto) {
        this.Comment=requestDto.getComment();
    }
}
