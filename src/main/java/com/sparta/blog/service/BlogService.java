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
    public List<ResponseDto> getBlogs() {
        return blogRepository.findAllByOrderByModifiedAtDesc().stream().map(ResponseDto::new).toList();
    }
    public ResponseDto getBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("글이 존재하지 않습니다."));
        return new ResponseDto(blog);
    }
    @Transactional
    public ResponseDto createBlog(RequestDto requestDto) {
        //dto->entity
        Blog blog = new Blog(requestDto);
        //entity->db
        blogRepository.save(blog);
        //entity->dto->controller
        return new ResponseDto(blog);
    }

    @Transactional
    public ResponseDto updateBlog(Long id, RequestDto requestDto) {
        Blog updateBlog = checkBlogAndPassword(id,requestDto);
        updateBlog.update(requestDto);
        return new ResponseDto(updateBlog);
    }

    @Transactional
    public String deleteBlog(Long id, RequestDto requestDto) {
        blogRepository.delete(checkBlogAndPassword(id,requestDto));
        return "삭제 되었습니다.";
    }

    public Blog checkBlogAndPassword(Long id, RequestDto requestDto){
        //blog check
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("글이 존재하지 않습니다."));
        //password check
        if(!blog.getPassword().equals(requestDto.getPassword())){
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }
        return blog;
    }
}
