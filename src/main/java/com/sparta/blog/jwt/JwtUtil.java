package com.sparta.blog.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 쿠키의 네임값

    public static final String AUTHORIZATION_KEY = "auth";
    // 사용자 권한 값의 KEY // 인가 not 인증


    public static final String BEARER_PREFIX = "Bearer ";
    // Token 식별자 약간 규칙같은거, 저게 붙어있으면 토큰이구나 라고 생각하면됨.,, value 즉 jwt 토큰 앞에 붙음. (뒤에 한칸 띔)

    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분
    // 토큰 만료시간

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    //properties에서 설정한 키
    //@Value 로 가져옴


    private Key key;
    //시크릿 키를 관리하는 key 인터페이스 객체 -> 즉 시크릿 키를 담음.


    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    //이건 hs245알고리즘을 사용하는데 이넘으로 되어있고, 뭐여 토큰을 암호화할 때 사용함


    // 로그 설정
    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");
    //로그 프로그램 상태를 주는건데 기존에 자바에서 sout으로 하는게 아니라 logger를 씀.
    //설정 방법 변수로 주거나 클래스에 @sl4fj 추가해서 사용할 수 있음.

    @PostConstruct
    //이건 딱 한번만 받아오면 될때, 사용함.
    //왜? jwt 생성을 하고, key에 시크릿 키를 담을거고,

    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        //시크릿키를 다시 디코등해서 바이트 배열로 받아서
        //키에 담아줌
        //다 알필요는 없는데 설명이 개떡같음.
        key = Keys.hmacShaKeyFor(bytes);
    }



    // Jwt 토큰 생성
    public String createToken(String username) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username) // 사용자 식별자값(ID)/ 또는 pk값
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }
    //즉 token = 베어리스 + 유저 식별자 값 + 권한(권한키,롤) + 만료시간(현재시간+만료시간) + 발급일 + 키(시크릿키,암호화 알고리즘)

    // JWT Cookie 에 저장
    public void addJwtToCookie(String token, HttpServletResponse res) {
        try {
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행
            //인코더를 사용해서 공백 지워주는거

            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token); // Name-Value 이름, 토큰
            cookie.setPath("/");


            // Response 객체에 Cookie 추가
            res.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
    }

    // JWT 토큰 substring
    //쿠키에 들어있는 jwt 를 꺼냄
    //클라이언트가 그냥 요청하면 쿠키는 request 객체에 들어오는 거임 그냥
    //토큰 조회할때 bar러스 짤라내는것
    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        logger.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            //이거 한줄로 토큰 검증 끝.
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    //검증을 해서 이상이 없다면.
    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        //바디에 있는 claims를 받환 (정보)
    }


    // 필터에서 토큰 가져오는 법
    // for문 돌려서 쿠키 이름 일일히 확인해서.
    // token 가져옴.
    // HttpServletRequest 에서 Cookie Value : JWT 가져오기
    public String getTokenFromRequest(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(AUTHORIZATION_HEADER)) {
                    try {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8"); // Encode 되어 넘어간 Value 다시 Decode
                    } catch (UnsupportedEncodingException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }

}

//유틸은 뭔가 그냥 음 기능들이 있는 모듈 느낌임
//jwt 기능들만 가진 클래스라고 생각하면됨.
//jwt 생성
//생성된 jwt 를 쿠키에 저장
// jwt 를 그냥 보내도 되고, 코드수가 줄겟죠 당연히,  header 에 바로 보낸다는데?

// jwt 쿠키에 넣고 response 에 담아서 보내줌 / 쿠키에 넣으면 만료기한도 줄수 있고, 다른 옵션들도 줄 수 있음.

//쿠키에 들어있는 jwt 를 꺼냄
// 클라이언트가 그냥 요청하면 쿠키는 request 객체에 들어오는 거임 그냥

//그 후 jwt 검증
// jwt 에서 정보 가져옴.