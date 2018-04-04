package com.lawliet.springboot.blog.service;

import com.lawliet.springboot.blog.domain.Authority;

/**
 * @author hao@lawliet.com
 * @since 2018/4/4 18:45
 */
public interface AuthorityService {
    /**
     * @author hao
     * @param [id]
     * @return com.lawliet.springboot.blog.domain.Authority
     */
    Authority getAuthorityById(Long id);

}
