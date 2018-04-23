package com.lawliet.springboot.blog.respository;

import com.lawliet.springboot.blog.domain.Catalog;
import com.lawliet.springboot.blog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author hao@lawliet.com
 * @since 2018/4/23 22:47
 */
public interface CatalogRepository  extends JpaRepository<Catalog,Long>{
    /**
     * 通过用户查询
     * @param [user]
     * @return java.util.List<com.lawliet.springboot.blog.domain.Catalog>
     */
    List<Catalog> findByUser(User user);

    /**
     * 通过用户和分类名称查询
     * @param [user, name]
     * @return java.util.List<com.lawliet.springboot.blog.domain.Catalog>
     */
    List<Catalog> findByUserAndName(User user,String name);

}
