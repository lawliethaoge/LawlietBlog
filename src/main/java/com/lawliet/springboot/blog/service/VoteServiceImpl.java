package com.lawliet.springboot.blog.service;

import com.lawliet.springboot.blog.domain.Blog;
import com.lawliet.springboot.blog.domain.User;
import com.lawliet.springboot.blog.domain.Vote;
import com.lawliet.springboot.blog.respository.BlogRepository;
import com.lawliet.springboot.blog.respository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author hao@lawliet.com
 * @since 2018/4/23 21:35
 */
@Service
public class VoteServiceImpl implements VoteService {
    private VoteRepository voteRepository;

    @Autowired
    public void setVoteRepository(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }


    @Override
    public Vote getVoteById(Long id) {
        return voteRepository.findOne(id);
    }

    @Override
    public void removeVote(Long id) {
        voteRepository.delete(id);

    }
}
