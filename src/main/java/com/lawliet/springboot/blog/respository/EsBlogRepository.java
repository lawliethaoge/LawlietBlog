package com.lawliet.springboot.blog.respository;

import com.lawliet.springboot.blog.domain.es.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;


/**
 * EsBlog Repository 接口
 *
 * @author hao@lawliet.com
 * @since 2018/2/1 15:54
 */

public interface EsBlogRepository extends ElasticsearchCrudRepository<EsBlog, String> {
    /**
     * 分页查询blog（去重）
     *
     * @param [title, Summary, content, tags, pageable]
     * @return org.springframework.data.domain.Page<com.lawliet.springboot.blog.domain.es.EsBlog>
     */
    Page<EsBlog> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(String title, String Summary, String content, String tags, Pageable pageable);

    EsBlog findByBlogId(Long blogId);

//    Page<EsBlog> findDistinctEsBlogByTitleContainingOrSummary(String keyword, String keyword1);
}
