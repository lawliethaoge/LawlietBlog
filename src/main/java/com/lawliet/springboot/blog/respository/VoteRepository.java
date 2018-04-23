package com.lawliet.springboot.blog.respository;

import com.lawliet.springboot.blog.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 点赞仓库
 * @author hao@lawliet.com
 * @since 2018/4/23 21:32
 */
public interface VoteRepository extends JpaRepository<Vote,Long> {
}
