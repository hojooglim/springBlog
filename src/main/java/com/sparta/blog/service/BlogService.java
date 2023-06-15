package com.sparta.blog.service;

import com.sparta.blog.dto.RequestDto;
import com.sparta.blog.dto.ResponseDto;
import com.sparta.blog.entity.Blog;
import com.sparta.blog.repository.BlogRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    BlogRepository blogRepository;
    public BlogService(BlogRepository blogRepository){
        this.blogRepository=blogRepository;
    }
    public List<ResponseDto> getBlog() {
        return blogRepository.findAllByOrderByModifiedAtDesc().stream().map(ResponseDto::new).toList();
    }
    public ResponseDto getBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("글이 존재하지 않습니다."));
        return new ResponseDto(blog);
    }

    public ResponseDto createBlog(RequestDto requestDto) {
        //dto -> entity
        Blog blog = new Blog(requestDto);
        //->repository
        blogRepository.save(blog);
        //entity->dto->controller
        return new ResponseDto(blog);
    }

    @Transactional
    public ResponseDto updateBlog(Long id, RequestDto requestDto) {
        //blog 확인
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("글이 존재하지 않습니다."));
        if(requestDto.getPassword().equals(blog.getPassword())){
            blog.update(requestDto);
        } else {
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }
        return new ResponseDto(blog);
    }


    public String deleteBlog(Long id, String password) {
        //blog 확인
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("글이 존재하지 않습니다."));
        if(blog.getPassword().equals(password)){
            blogRepository.delete(blog);
        } else {
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }
        return "ture";
    }
}
