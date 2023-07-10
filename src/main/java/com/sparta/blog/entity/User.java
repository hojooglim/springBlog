package com.sparta.blog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    //이넘 타입을 데이터 베이스에 저장할때 사용.
    //이넘의 이름 그대로를 저장
    private UserRoleEnum role;


    public User(String username, String password, String email, UserRoleEnum role) {
        this.username=username;
        this.password=password;
        this.email=email;
        this.role=role;
    }

}
