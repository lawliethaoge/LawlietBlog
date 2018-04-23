package com.lawliet.springboot.blog.respository;

import com.lawliet.springboot.blog.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 评论仓库
 * @author hao@lawliet.com
 * @since 2018/4/22 21:34
 */
public interface CommitRepository extends JpaRepository<Comment,Long>{

}
