package com.lawliet.springboot.blog.service;

import com.lawliet.springboot.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

/**
 * 用户服务接口
 *
 * @author hao@lawliet.com
 * @since 2018/3/24 15:39
 */
public interface UserService {

    /**
     * 新增、编辑、保存用户
     * @author hao
     * @param [user]
     * @return com.lawliet.springboot.blog.domain.User
     */
    User saveOrUpdateUser(User user);

    /**
     * 注册用户
     * @author hao
     * @param [user]
     * @return com.lawliet.springboot.blog.domain.User
     */
    User registerUser(User user);

    /**
     * 删除用户
     * @author hao
     * @param [id]
     * @return void
     */
    void removeUser(Long id);

    /**
     * 根据id查找用户
     * @author hao
     * @param [id]
     * @return com.lawliet.springboot.blog.domain.User
     */
    User findUserById(Long id);

    /**
     * 根据用户名模糊查询用户
     * @author hao
     * @param [name, pageable]
     * @return org.springframework.data.domain.Page<com.lawliet.springboot.blog.domain.User>
     */
    Page<User> listUsersByNameLike(String name, Pageable pageable);

    /**
     * @author hao
     * @param [pageable]
     * @return org.springframework.data.domain.Page<com.lawliet.springboot.blog.domain.User>
     */
    Page<User> findAll(Pageable pageable);

    /**
     * 根据名称列表用户查询
     * @param usernames
     * @return
     */
    List<User> listUsersByUsernames(Collection<String> usernames);

}
