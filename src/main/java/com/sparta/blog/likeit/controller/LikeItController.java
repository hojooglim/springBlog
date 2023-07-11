package com.sparta.blog.likeit.controller;


import com.sparta.blog.likeit.service.LikeItService;
import com.sparta.blog.security.filter.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeItController {
    private final LikeItService likeitService;

    @PostMapping("/blogLike/{blog_id}")
    public void blogLike(@PathVariable Long blog_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        likeitService.blogLike(blog_id,userDetails);
    }

    @PostMapping("/commentLike/{comment_id}")
    public void commentLike(@PathVariable Long comment_id,@AuthenticationPrincipal UserDetailsImpl userDetails){
        likeitService.commentLike(comment_id,userDetails);
    }

}
