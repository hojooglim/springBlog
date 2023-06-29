package com.sparta.blog.filter;

import com.sparta.blog.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증")
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    //extends OncePerRequestFilter http서블릿 리퀘스트 쓸수 잇음?

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        //클라이언트에게 받아온 토큰
        //1)header name-value 일 경우 value만 꺼내서 bar부분 잘라주는 것 까지
        String tokenValue = jwtUtil.getJwtFromHeader(req);
        //2-1)쿠키로 받아올 경우 value만 가져옴
        //String tokenValue = jwtUtil.getTokenFromRequest(req);

        if (StringUtils.hasText(tokenValue)) {
//            //2-2)value JWT 토큰 substring
//            tokenValue = jwtUtil.substringToken(tokenValue);
             log.info(tokenValue);

            //토큰 검증 (key)
            if (!jwtUtil.validateToken(tokenValue)) {
                log.error("Token Error");
                return;
            }

            //검증된 토큰을 통해 정보 가져옴.(클레임 타입)
            Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

            try {
                //인증처리 (유저 아이디 필요)
                //잘모르겠음.
                setAuthentication(info.getSubject());
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
            log.info("인증 완료");
        }

        filterChain.doFilter(req, res);
    }

    // 인증 처리
    public void setAuthentication(String username) {
        //2) 홀더에 authentication 넣어줌
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        //1) 클레입 타입의 유저 네임을 통해 authentication 인증 객체(정보가 들어있음 userdetail) 만들고
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // 토큰에 유저 권한
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}