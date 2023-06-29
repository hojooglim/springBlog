package com.sparta.blog.service;

import com.sparta.blog.dto.blog.BlogRequestDto;
import com.sparta.blog.dto.blog.BlogResponseDto;
import com.sparta.blog.dto.blog.DeleteResponseDto;
import com.sparta.blog.entity.Blog;
import com.sparta.blog.entity.User;
import com.sparta.blog.repository.BlogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public BlogResponseDto createBlog(BlogRequestDto blogRequestDto, User user) {
        return new BlogResponseDto(blogRepository.save(new Blog(blogRequestDto, user)));
    }//dto->entity->db->entity->dto->controller

    public List<BlogResponseDto> getBlogs() {
        return blogRepository.findAllByOrderByModifiedAtDesc().stream().map(BlogResponseDto::new).toList();
    }

    public BlogResponseDto getBlog(Long id) {
        return new BlogResponseDto(checkBlog(id));
    }

    @Transactional
    public BlogResponseDto updateBlog(Long id, BlogRequestDto blogRequestDto) {
        Blog blog = checkBlog(id);
        Blog updateBlog = blog.update(blogRequestDto);
        return new BlogResponseDto(updateBlog);
    }

    public DeleteResponseDto deleteBlog(Long id) {
        blogRepository.delete(checkBlog(id));
        return new DeleteResponseDto("Delete Success",200);
    }

    public Blog checkBlog(Long id){
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("글이 존재하지 않습니다."));
        return blog;
    } //blog check

}
