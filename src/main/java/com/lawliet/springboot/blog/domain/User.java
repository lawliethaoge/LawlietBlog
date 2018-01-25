package com.lawliet.springboot.blog.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author hao@lawliet.com
 * @since 2018/1/22 21:29
 */
@Entity//实体
public class User {
    @Id//主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键策略（自增）
    private Long id;//唯一标识
    private String name;//名称
    private String email;//邮箱

    //有参/无参构造,设置成 protect 防止直接使用


    protected User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    protected User() {

    }

    public User(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
