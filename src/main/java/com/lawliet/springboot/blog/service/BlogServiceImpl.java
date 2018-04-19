package com.lawliet.springboot.blog.service;

import com.lawliet.springboot.blog.domain.Blog;
import com.lawliet.springboot.blog.domain.User;
import com.lawliet.springboot.blog.respository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hao@lawliet.com
 * @since 2018/4/15 18:43
 */

@Service
public class BlogServiceImpl implements BlogService {

    private BlogRepository blogRepository;

    @Autowired
    public void setBlogRepository(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        Blog blog1 = blogRepository.save(blog);
        return blog1;
    }

    @Transactional
    @Override
    public void removeBlog(Long id) {
        blogRepository.delete(id);

    }

    @Override
    public Blog getBlogById(Long id) {
        Blog blog = blogRepository.findOne(id);
        return blog;
    }

    @Override
    public Page<Blog> ListBlogsByTitleAndTime(User user, String title, Pageable pageable) {
        title = "%" + title + "%";
        String tags = title;
        Page<Blog> blogs = blogRepository.findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(title,user,tags,user,pageable);
        return blogs;
    }

    @Override
    public Page<Blog> ListBlogsByTitleAndVote(User user, String title, Pageable pageable) {
        title = "%" + title + "%";
        Page<Blog> blogs = blogRepository.findByUserAndTitleLike(user,title,pageable);
        return blogs;
    }

    @Override
    public void ReadingIncrease(Long id) {
        Blog blog = blogRepository.findOne(id);
        blog.setReadSize(blog.getReadSize()+1);            //阅读量自增
        this.saveBlog(blog);

    }
}
