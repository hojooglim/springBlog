package com.sparta.blog.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.blog.dto.login.LoginRequestDto;
import com.sparta.blog.dto.login.LoginResponseDto;
import com.sparta.blog.entity.UserRoleEnum;
import com.sparta.blog.jwt.JwtUtil;
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
    //UsernamePasswordAuthenticationFilter extends
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/users/login");
        //controller login xx
        //filter login urlPath
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        // HttpServletRequest -> LoginRequestDto (get id,password) -> UsernamePasswordAuthenticationToken (not jwt token)-> Manger -> 인증
        // -> 인증 완료 -> SecurityContextHolder안에 SecurityContext에 회원 정보(entity) 저장
        // 회원 정보를 저장한다 -> 매니저를 통해 인증이 완료되면 -> UserDetailsService-> Repository조회 -> data -> UserDetail
        // {UserDetail->(principal)}이 Holder안에 저장.
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
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

        //Holder안에 저장된 UserDetails로 유저 정보 가져와 JWT토큰을 생성

        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(username,role);
        //1)토큰을 쿠키에 넣어서 리스폰 객체에 넣어서 보내주는 방법
        //jwtUtil.addJwtToCookie(token, response);

        //2)header에 name-value(token) 으로 보내주는 방법
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER,token);

        LoginResponseDto loginRequestDto = jwtUtil.loginSuccess();
        response.getOutputStream().println(loginRequestDto.getMsg());
        response.getOutputStream().println("status Code : "+loginRequestDto.getStatusCode());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(401);
    }
}