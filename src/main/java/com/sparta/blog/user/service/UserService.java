package com.sparta.blog.user.service;

import com.sparta.blog.user.dto.SignRequestDto;
import com.sparta.blog.user.entity.User;
import com.sparta.blog.user.entity.UserRoleEnum;
import com.sparta.blog.security.jwt.JwtUtil;
import com.sparta.blog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void signup(SignRequestDto signRequestDto){

        Optional<User> checkUserName = userRepository.findByUsername(signRequestDto.getUsername());
        if (checkUserName.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }


        Optional<User> checkEmail = userRepository.findByEmail(signRequestDto.getEmail());
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }


        String password = passwordEncoder.encode(signRequestDto.getPassword());

        UserRoleEnum role = UserRoleEnum.USER;
        //admin 조건 if

        User user = new User(signRequestDto.getUsername(), password, signRequestDto.getEmail(),role);
        userRepository.save(user);
    }


}
