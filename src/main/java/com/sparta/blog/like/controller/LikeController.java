package com.sparta.blog.like.controller;


import com.sparta.blog.like.service.LikeService;
import com.sparta.blog.security.filter.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/blogLike/{blog_id}")
    public void blogLike(@PathVariable Long blog_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        likeService.blogLike(blog_id,userDetails);
    }

//    @PostMapping("/commentLike/{comment_id}")
//    public void commentLike(@PathVariable Long comment_id,@AuthenticationPrincipal UserDetailsImpl userDetails){
//
//    }

}
