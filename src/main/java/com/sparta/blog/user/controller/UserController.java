package com.sparta.blog.user.controller;

import com.sparta.blog.user.dto.SignRequestDto;
import com.sparta.blog.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignRequestDto signRequestDto){
        userService.signup(signRequestDto);
        return new ResponseEntity<>("회원가입에 성공하였습니다.", HttpStatus.OK);
    }

}
