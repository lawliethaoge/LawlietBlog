package com.lawliet.springboot.blog.service;

import com.lawliet.springboot.blog.domain.Authority;
import com.lawliet.springboot.blog.respository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author hao@lawliet.com
 * @since 2018/4/4 18:56
 */
public class AuthorityServiceImpl implements AuthorityService {
    private AuthorityRepository authorityRepository;

    @Autowired
    public void setAuthorityRepository(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Authority getAuthorityById(Long id) {
        return authorityRepository.findOne(id);
    }
}
