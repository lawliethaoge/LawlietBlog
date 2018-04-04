package com.lawliet.springboot.blog.service;

import com.lawliet.springboot.blog.domain.User;
import com.lawliet.springboot.blog.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务接口实现
 * @author hao@lawliet.com
 * @since 2018/3/24 16:16
 */
@Service
public class UserServiceImpl implements UserService{


    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional    //事务处理
    @Override
    public User saveOrUpdateUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void removeUser(Long id) {
        userRepository.delete(id);
    }

    @Override
    public User findUserById(Long id) {

        return userRepository.findOne(id);
    }

    @Override
    public Page<User> listUsersByNameLike(String name, Pageable pageable) {

        name ="%"+ "name" +"%";
        Page<User>  users = userRepository.findByNameLike(name,pageable);
        return users;
    }
}
