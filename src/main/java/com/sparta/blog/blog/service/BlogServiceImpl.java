package com.sparta.blog.blog.service;

import com.sparta.blog.blog.dto.BlogRequestDto;
import com.sparta.blog.blog.dto.BlogResponseDto;
import com.sparta.blog.blog.entity.Blog;
import com.sparta.blog.blog.repository.BlogRepository;
import com.sparta.blog.exception.AuthUserException;
import com.sparta.blog.security.filter.UserDetailsImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService{

    private final BlogRepository blogRepository;

    @Override
    @Transactional(readOnly= true)
    public BlogResponseDto getBlog(Long id) {
        return new BlogResponseDto(findBlog(id));
    }

    @Override
    @Transactional(readOnly= true)
    public List<BlogResponseDto> getBlogs() {
        return blogRepository.findAllByOrderByModifiedAtDesc().stream().map(BlogResponseDto::new).toList();
    }

    @Override
    public BlogResponseDto createBlog(BlogRequestDto blogRequestDto, UserDetailsImpl userDetails) {
        return new BlogResponseDto(blogRepository.save(new Blog(blogRequestDto, userDetails.getUser())));
    }

    @Override
    @Transactional
    public BlogResponseDto updateBlog(Long id, BlogRequestDto blogRequestDto,UserDetailsImpl userDetails) {
        Blog blog = findBlog(id);
        if(authUser(blog, userDetails)){
            blog.update(blogRequestDto);
        }else {
            throw new AuthUserException("작성자만 삭제/수정할 수 있습니다.");
        }
        return new BlogResponseDto(blog);
    }

    @Override
    @Transactional
    public void deleteBlog(Long id, UserDetailsImpl userDetails) {
        Blog blog = findBlog(id);
        if(authUser(blog, userDetails)){
            blogRepository.delete(blog);
        } else {
            throw new AuthUserException("작성자만 삭제/수정할 수 있습니다.");
        }
    }

    @Override
    @Transactional(readOnly= true)
    public Blog findBlog(Long id){
        return blogRepository.findById(id).orElseThrow(() -> new NullPointerException("글이 존재하지 않습니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean authUser(Blog blog, UserDetailsImpl userDetails){
        return blog.getUser().getId().equals(userDetails.getUser().getId());
    }

}
