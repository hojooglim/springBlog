package com.sparta.blog.service;

import com.sparta.blog.dto.LoginRequestDto;
import com.sparta.blog.dto.SignRequestDto;
import com.sparta.blog.dto.SignResponseDto;
import com.sparta.blog.entity.User;
import com.sparta.blog.entity.UserRoleEnum;
import com.sparta.blog.jwt.JwtUtil;
import com.sparta.blog.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;

    }



    public void signup(SignRequestDto signRequestDto){
        //회원가입 요청
        //id 중복 확인
        Optional<User> checkUserName = userRepository.findByUsername(signRequestDto.getUsername());
        if (checkUserName.isPresent()) {
            // Optional에 있는 isPresent 중복 확인하는 메서드
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        //email 중복 확인
        Optional<User> checkEmail = userRepository.findByEmail(signRequestDto.getEmail());
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        //비밀번호 암호화 (string security)
        String password = passwordEncoder.encode(signRequestDto.getPassword());

        UserRoleEnum role = UserRoleEnum.USER;

        User user = new User(signRequestDto.getUsername(), password, signRequestDto.getEmail(),role);
        userRepository.save(user);
    }


    public void login(LoginRequestDto loginRequestDto, HttpServletResponse res) {
        //로그인 요청  // 이게 노필터 방식
        //아이디 확인
        User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(
                ()-> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        //비밀번호 확인
        if(!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        //토큰 생성
        String token = jwtUtil.createToken(user.getUsername(),user.getRole());
        //토큰을 쿠키에 담아서, response로 보내준다
        jwtUtil.addJwtToCookie(token, res);
    }
}
