package com.sparta.blog.security;

import com.sparta.blog.entity.User;
import com.sparta.blog.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //즉, 시큐리티 필터에서, 로그인을 하고 인증을 해주는데.
    //인증이 되면, 유저내임 즉 아이디를 가지고 레포지토리에서 정보를 데이터를 가져와서
    //객체로 만든 다음에.
    //여기는 서비스니까
    //유저 디테일로 객체를 보내줌.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //유저가 있는지 없는지 화인하고
        //유저 객체 보내줌
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + username));

        return new UserDetailsImpl(user);
    }
}
