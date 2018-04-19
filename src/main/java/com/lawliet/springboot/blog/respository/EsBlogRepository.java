package com.lawliet.springboot.blog.respository;

import com.lawliet.springboot.blog.domain.EsBlog;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;


/**
 * EsBlog Repository 接口
 *
 * @author hao@lawliet.com
 * @since 2018/2/1 15:54
 */

public interface EsBlogRepository extends ElasticsearchCrudRepository<EsBlog,String> {
    //分页查询blog（去重）
    Page<EsBlog> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining(String title, String summary, String content, Pageable pageable);
}
