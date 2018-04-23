package com.lawliet.springboot.blog.respository;

import com.lawliet.springboot.blog.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.net.UnknownServiceException;
import java.util.Collection;
import java.util.List;

/**
 * @author hao@lawliet.com
 * @since 2018/1/22 21:32
 */

public interface UserRepository extends JpaRepository<User, Long> {



    /**
     * 根据用户名查询用户列表
     * @author hao
     * @param [name, pageable]
     * @return org.springframework.data.domain.Page<com.lawliet.springboot.blog.domain.User>
     */
    Page<User> findByNameLike(String name, Pageable pageable);


    /**
     * @author hao
     * @param [pageable]
     * @return org.springframework.data.domain.Page<com.lawliet.springboot.blog.domain.User>
     */
    Page<User> findAll(Pageable pageable);


    /**
     * 根据用户账号查询用户
     * @author hao
     * @param [username]
     * @return com.lawliet.springboot.blog.domain.User
     */
    User findByUsername(String username);

    /**
     * 根据名称列表查询
     * @param [usernames]
     * @return java.util.List<com.lawliet.springboot.blog.domain.User>
     */
    List<User> findByUsernameIn(Collection<String> usernames);


}
