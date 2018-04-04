package com.lawliet.springboot.blog.respository;

import com.lawliet.springboot.blog.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hao@lawliet.com
 * @since 2018/4/4 18:57
 */
public interface AuthorityRepository extends JpaRepository<Authority,Long> {

    Authority findAuthoritiesById(Long id);
}
