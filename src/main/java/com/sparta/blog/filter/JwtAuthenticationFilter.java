package com.sparta.blog.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.blog.dto.LoginRequestDto;
import com.sparta.blog.dto.LoginResponseDto;
import com.sparta.blog.entity.UserRoleEnum;
import com.sparta.blog.jwt.JwtUtil;
import com.sparta.blog.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    //username passwerd 필터를 상속 받아와서 사용함.
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/users/login");
        //상속 받으면 이 메서드도 같이 씀.
        //왜냐면 로그인이 우리가 지정한 로그인 유알엘이라서
        //저기 유알엘에서 인증 확인이 시작됨.
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        //usernamepasswordAnthenticaitionfilter 아이디 비밀 번호 확인 후 토큰(은 홀더에 저장) 생성, 매니저에게 전달, 인증
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
            log.info("dto 생성");
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");


        //class 새로 제작해서 변수 메세지랑 상태코드 dto 비스무리한거
        //상단 로직에서 로그인이 성공하면 홀더에 토큰이 저장되고 저장된 토큰에서 principal에 유저디테일 저장.
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();
        //토큰이랑 아이디로 jwt 토큰 만듬.
        //쿠키에 넣어서 리스폰 객체에 넣어서 보내줌

        String token = jwtUtil.createToken(username,role);
        LoginResponseDto loginRequestDto = jwtUtil.addJwtToCookie(token, response);
        response.getOutputStream().println(loginRequestDto.getMsg());
        response.getOutputStream().println("status Code : "+loginRequestDto.getStatusCode());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(401);
    }
}