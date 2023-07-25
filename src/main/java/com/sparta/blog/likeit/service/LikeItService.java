package com.sparta.blog.likeit.service;

import com.sparta.blog.security.filter.UserDetailsImpl;

public interface LikeItService {

    void blogLike(Long blogId, UserDetailsImpl userDetails);

    void commentLike(Long commentId, UserDetailsImpl userDetails);
}
