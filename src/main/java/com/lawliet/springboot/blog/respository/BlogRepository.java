package com.lawliet.springboot.blog.respository;

import com.lawliet.springboot.blog.domain.Blog;
import com.lawliet.springboot.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Blog仓库
 * @author hao@lawliet.com
 * @since 2018/4/15 18:19
 */
public interface BlogRepository extends JpaRepository<Blog,Long> {
    /**
     *  根据用户名，博客标题分页查询博客列表
     * @author hao
     * @param [user, title, pageable]
     * @return org.springframework.data.domain.Page<com.lawliet.springboot.blog.domain.Blog>
     */
    Page<Blog> findByUserAndTitleLike(User user, String title, Pageable pageable);


    /**
     * 根据用户名，博客标签查询博客列表（时间逆序）
     * @author hao
     * @param [title, user, pageable]
     * @return org.springframework.data.domain.Page<com.lawliet.springboot.blog.domain.Blog>
     */
    Page<Blog> findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(String title, User user, String tags, User user2, Pageable pageable);


}
