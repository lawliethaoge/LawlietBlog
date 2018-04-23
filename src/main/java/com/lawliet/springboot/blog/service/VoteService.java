package com.lawliet.springboot.blog.service;

import com.lawliet.springboot.blog.domain.Blog;
import com.lawliet.springboot.blog.domain.Vote;

/**
 * @author hao@lawliet.com
 * @since 2018/4/23 21:33
 */
public interface VoteService {
    /**
     * 根据id获取 Vote
     * @author hao
     * @param [id]
     * @return com.lawliet.springboot.blog.domain.Vote
     */
    Vote getVoteById(Long id);


    /**
     * 取消点赞
     * @author hao
     * @param [id]
     * @return void
     */
    void removeVote(Long id);

}
