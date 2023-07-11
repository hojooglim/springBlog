package com.sparta.blog.user.controller;

import com.sparta.blog.user.dto.SignRequestDto;
import com.sparta.blog.user.dto.SignResponseDto;
import com.sparta.blog.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public SignResponseDto signup(@RequestBody @Valid SignRequestDto signRequestDto){
        userService.signup(signRequestDto);
        return new SignResponseDto("SignUp Success",200);
    }
    //중복 예외
}
