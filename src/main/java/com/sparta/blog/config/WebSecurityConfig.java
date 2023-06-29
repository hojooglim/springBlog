package com.sparta.blog.config;

import com.sparta.blog.filter.JwtAuthenticationFilter;
import com.sparta.blog.filter.JwtAuthorizationFilter;
import com.sparta.blog.jwt.JwtUtil;
import com.sparta.blog.filter.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    //인증 매니저 configuration

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }
    //jwt 인증 Filter (login, jwtCreate)

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        JwtAuthorizationFilter filter = new JwtAuthorizationFilter(jwtUtil, userDetailsService);
        return filter;
    }
    //jwt인가 Filter (jwt check), save token in holder

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정 사용안하고
        // CSRF 무엇인지 알아보기
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources static 접근 허용 설정
                        .requestMatchers("/api/users/**").permitAll() // '/api/user/'로 시작하는 요청 모두 접근 허가
                        .requestMatchers("/api/blogs/**").permitAll() // '/api/blog'(get All blog) 접근 모두 허가
                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리
        );


        //formLogin not using
//        http.formLogin((formLogin) ->
//                formLogin
//                        .loginPage("/api/user/login").permitAll()
//        );



        // filter zation->cation?
        // 확인 해봐야함.
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);



//        // 접근 불가 페이지 not using
//        http.exceptionHandling((exceptionHandling) ->
//                exceptionHandling
//                        // "접근 불가" 페이지 URL 설정
//                        .accessDeniedPage("/forbidden.html")
//        );

        return http.build();
    }
}