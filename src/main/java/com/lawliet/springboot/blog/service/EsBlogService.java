package com.lawliet.springboot.blog.service;

import com.lawliet.springboot.blog.domain.User;
import com.lawliet.springboot.blog.domain.es.EsBlog;
import com.lawliet.springboot.blog.vo.TagVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author hao@lawliet.com
 * @since 2018/4/24 1:55
 */
public interface EsBlogService {
    /**
     * 删除Blog
     * @param id
     * @return
     */
    void removeEsBlog(String id);

    /**
     * 更新 EsBlog
     * @param [esBlog]
     * @return com.lawliet.springboot.blog.domain.es.EsBlog
     */
    EsBlog updateEsBlog(EsBlog esBlog);

    /**
     * 根据id获取Blog
     * @param [blogId]
     * @return com.lawliet.springboot.blog.domain.es.EsBlog
     */
    EsBlog getEsBlogByBlogId(Long blogId);

    /**
     * 最新博客列表，分页
     * @param keyword
     * @param pageable
     * @return
     */
    Page<EsBlog> listNewestEsBlogs(String keyword, Pageable pageable);

    /**
     * 最热博客列表，分页
     * @param keyword
     * @param pageable
     * @return
     */
    Page<EsBlog> listHotestEsBlogs(String keyword, Pageable pageable);

    /**
     * 博客列表，分页
     * @param pageable
     * @return
     */
    Page<EsBlog> listEsBlogs(Pageable pageable);

    /**
     * 最新前5
     * @param []
     * @return java.util.List<com.lawliet.springboot.blog.domain.es.EsBlog>
     */
    List<EsBlog> listTop5NewestEsBlogs();

    /**
     * 最热前5
     * @param []
     * @return java.util.List<com.lawliet.springboot.blog.domain.es.EsBlog>
     */
    List<EsBlog> listTop5HotestEsBlogs();

    /**
     * 最热前 30 标签
     * @return
     */
    List<TagVO> listTop30Tags();

    /**
     * 最热前12用户
     * @return
     */
    List<User> listTop12Users();
}
