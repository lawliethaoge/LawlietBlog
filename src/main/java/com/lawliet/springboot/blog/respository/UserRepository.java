package com.lawliet.springboot.blog.respository;

import com.lawliet.springboot.blog.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author hao@lawliet.com
 * @since 2018/1/22 21:32
 */
public interface UserRepository extends CrudRepository<User,Long>{
}
