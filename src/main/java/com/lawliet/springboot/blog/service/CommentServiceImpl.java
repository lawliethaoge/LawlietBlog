package com.lawliet.springboot.blog.service;

import com.lawliet.springboot.blog.domain.Comment;
import com.lawliet.springboot.blog.respository.CommitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hao@lawliet.com
 * @since 2018/4/22 21:38
 */
@Service
public class CommentServiceImpl implements CommentService {


    private CommitRepository commitRepository;

    @Autowired
    public void setCommitRepository(CommitRepository commitRepository) {
        this.commitRepository = commitRepository;
    }

    @Override
    public Comment getCommentById(Long id) {
        return commitRepository.findOne(id);
    }

    @Override
    public void removeComment(Long id) {
        commitRepository.delete(id);

    }
}
