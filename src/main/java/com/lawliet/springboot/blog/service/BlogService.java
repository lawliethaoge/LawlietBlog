package com.lawliet.springboot.blog.service;

import com.lawliet.springboot.blog.domain.Blog;
import com.lawliet.springboot.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author hao@lawliet.com
 * @since 2018/4/15 18:34
 */

public interface BlogService {
    /**
     * 保存博客
     * @author hao
     * @param [blog]
     * @return com.lawliet.springboot.blog.domain.Blog
     */
    Blog saveBlog(Blog blog);

    /**
     * 删除博客
     * @author hao
     * @param [id]
     * @return void
     */
    void removeBlog(Long id);

    /**
     * 通过id查找博客
     * @author hao
     * @param [id]
     * @return com.lawliet.springboot.blog.domain.Blog
     */
    Blog getBlogById(Long id);

    /**
     * 通过用户，标题查找博客（最新）
     * @author hao
     * @param [user, title, pageable]
     * @return org.springframework.data.domain.Page<com.lawliet.springboot.blog.domain.Blog>
     */
    Page<Blog> ListBlogsByTitleAndTime(User user, String title, Pageable pageable);

    /**
     * 通过用户，标题查找博客（最热）
     * @author hao
     * @param [user, title, pageable]
     * @return org.springframework.data.domain.Page<com.lawliet.springboot.blog.domain.Blog>
     */
    Page<Blog> ListBlogsByTitleAndVote(User user, String title, Pageable pageable);

    /**
     * 阅读量递增
     * @author hao
     * @param [id]
     * @return void
     */
    void ReadingIncrease(Long id);


}
