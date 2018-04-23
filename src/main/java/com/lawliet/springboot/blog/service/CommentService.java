package com.lawliet.springboot.blog.service;

import com.lawliet.springboot.blog.domain.Comment;

/**
 * @author hao@lawliet.com
 * @since 2018/4/22 21:35
 */
public interface CommentService {
    /**
     * 根据id获取comment
     * @author hao
     * @param [id]
     * @return com.lawliet.springboot.blog.domain.Comment
     */
    Comment getCommentById(Long id);

    /**
     * 根据id删除comment
     * @author hao
     * @param [id]
     * @return void
     */
    void removeComment(Long id);

}
