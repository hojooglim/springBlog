package com.sparta.blog.controller;

import com.sparta.blog.dto.SignRequestDto;
import com.sparta.blog.dto.SignResponseDto;
import com.sparta.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public SignResponseDto signup(@ModelAttribute @Valid SignRequestDto signRequestDto){
        userService.signup(signRequestDto);
        return new SignResponseDto("SignUp Success",200);
    }

//    @PostMapping("/login")
//    public String login(@ModelAttribute LoginRequestDto loginRequestDto, HttpServletResponse res){
//        //로그인에 성공한다면 로그인 성공 메세지
//        //로그인 실패시, 실패 메세지
//        try {
//            userService.login(loginRequestDto,res);
//        } catch (Exception e) {
//            return "로그인에 실패하였습니다.";
//        }
//        return "로그인에 성공하였습니다.";
//
//    }

}
