package com.lawliet.springboot.blog.service;

import com.lawliet.springboot.blog.domain.Blog;
import com.lawliet.springboot.blog.domain.Catalog;
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
     *
     * @param [blog]
     * @return com.lawliet.springboot.blog.domain.Blog
     * @author hao
     */
    Blog saveBlog(Blog blog);

    /**
     * 删除博客
     *
     * @param [id]
     * @return void
     * @author hao
     */
    void removeBlog(Long id);

    /**
     * 通过id查找博客
     *
     * @param [id]
     * @return com.lawliet.springboot.blog.domain.Blog
     * @author hao
     */
    Blog getBlogById(Long id);

    /**
     * @param [title, pageable]
     * @return org.springframework.data.domain.Page<com.lawliet.springboot.blog.domain.Blog>
     */
    Page<Blog> ListBlogsByTitle(String title, Pageable pageable);

    /**
     * 通过用户，标题查找博客（最新）
     *
     * @param [user, title, pageable]
     * @return org.springframework.data.domain.Page<com.lawliet.springboot.blog.domain.Blog>
     * @author hao
     */
    Page<Blog> ListBlogsByTitleAndTime(User user, String title, Pageable pageable);

    /**
     * 通过用户，标题查找博客（最热）
     *
     * @param [user, title, pageable]
     * @return org.springframework.data.domain.Page<com.lawliet.springboot.blog.domain.Blog>
     * @author hao
     */
    Page<Blog> ListBlogsByTitleAndVote(User user, String title, Pageable pageable);


    /**
     * 通过分类查找博客
     * @param [catalog, pageable]
     * @return org.springframework.data.domain.Page<com.lawliet.springboot.blog.domain.Blog>
     */
    Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable);

    /**
     * 阅读量递增
     *
     * @param [id]
     * @return void
     * @author hao
     */
    void ReadingIncrease(Long id);

    /**
     * 发表评论
     *
     * @param [blogId, commentContent]
     * @return com.lawliet.springboot.blog.domain.Blog
     * @author hao
     */
    Blog createComment(Long blogId, String commentContent);

    /**
     * 删除评论
     *
     * @param [blogId, commentId]
     * @return void
     * @author hao
     */
    void removeComment(Long blogId, Long commentId);

    /**
     * 点赞
     *
     * @param [blogId]
     * @return com.lawliet.springboot.blog.domain.Blog
     * @author hao
     */
    Blog createVote(Long blogId);

    /**
     * 取消点赞
     *
     * @param [blogId, voteId]
     * @return void
     * @author hao
     */
    void removeVote(Long blogId, Long voteId);


}
