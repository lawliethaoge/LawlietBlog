package com.lawliet.springboot.blog.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * @author hao@lawliet.com
 * @since 2018/4/4 18:48
 */
@Entity
public class Authority implements GrantedAuthority {
//    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增长
    private Long id;


    @Column(nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 获得权限
    public String getAuthority(){
        return name;
    }
}
