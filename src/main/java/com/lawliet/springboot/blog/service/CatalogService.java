package com.lawliet.springboot.blog.service;

import com.lawliet.springboot.blog.domain.Catalog;
import com.lawliet.springboot.blog.domain.User;

import java.util.List;

/**
 * @author hao@lawliet.com
 * @since 2018/4/23 22:56
 */
public interface CatalogService {
    /**
     * 保存Catalog
     *
     * @param [catalog]
     * @return com.lawliet.springboot.blog.domain.Catalog
     */
    Catalog saveCatalog(Catalog catalog);

    /**
     * 删除Catalog
     *
     * @param [id]
     * @return void
     */
    void removeCatalog(Long id);

    /**
     * 根据id获取Catalog
     *
     * @param [id]
     * @return com.lawliet.springboot.blog.domain.Catalog
     */
    Catalog getCatalogById(Long id);

    /**
     * 获取Catalog列表
     *
     * @param [user]
     * @return java.util.List<com.lawliet.springboot.blog.domain.Catalog>
     */
    List<Catalog> listCatalogs(User user);
}
