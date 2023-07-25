package com.sparta.blog.likeit.controller;


import com.sparta.blog.likeit.service.LikeItServiceImpl;
import com.sparta.blog.security.filter.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeItController {

    private final LikeItServiceImpl likeitService;

    @PostMapping("/blogLike/{blog_id}")
    public ResponseEntity<String> blogLike(@PathVariable Long blog_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        likeitService.blogLike(blog_id,userDetails);
        return new ResponseEntity<>("좋아요!", HttpStatus.OK);
    }

    @PostMapping("/commentLike/{comment_id}")
    public ResponseEntity<String> commentLike(@PathVariable Long comment_id,@AuthenticationPrincipal UserDetailsImpl userDetails){
        likeitService.commentLike(comment_id,userDetails);
        return new ResponseEntity<>("좋아요!", HttpStatus.OK);
    }

}
