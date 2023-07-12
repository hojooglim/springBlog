package com.sparta.blog.blog.service;

import com.sparta.blog.blog.dto.BlogRequestDto;
import com.sparta.blog.blog.dto.BlogResponseDto;
import com.sparta.blog.blog.entity.Blog;
import com.sparta.blog.blog.repository.BlogRepository;
import com.sparta.blog.security.filter.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    public BlogResponseDto getBlog(Long id) {
        return new BlogResponseDto(checkBlog(id));
    }
    public List<BlogResponseDto> getBlogs() {
        return blogRepository.findAllByOrderByModifiedAtDesc().stream().map(BlogResponseDto::new).toList();
    }
    public BlogResponseDto createBlog(BlogRequestDto blogRequestDto, UserDetailsImpl userDetails) {
        return new BlogResponseDto(blogRepository.save(new Blog(blogRequestDto, userDetails.getUser())));
    }


    @Transactional
    public BlogResponseDto updateBlog(Long id, BlogRequestDto blogRequestDto,UserDetailsImpl userDetails) {
        Blog blog = checkBlog(id);
        if(blog.getId().equals(userDetails.getUser().getId())){
            blog.update(blogRequestDto);
        }else {
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }
        return new BlogResponseDto(blog);
    }

    public BlogResponseDto deleteBlog(Long id, UserDetailsImpl userDetails) {
        Blog blog = checkBlog(id);
        if(blog.getId().equals(userDetails.getUser().getId())){
            blogRepository.delete(blog);
        }else {
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }
        return new BlogResponseDto(blog);
    }

    public Blog checkBlog(Long id){
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new NullPointerException("글이 존재하지 않습니다."));
        return blog;
    }

}
