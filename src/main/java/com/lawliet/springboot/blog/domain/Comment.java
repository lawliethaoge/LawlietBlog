package com.lawliet.springboot.blog.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * commit 实体
 * @author hao@lawliet.com
 * @since 2018/4/22 21:08
 */
@Entity
public class Commit implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    private Long id; // 用户的唯一标识

    @NotEmpty(message = "评论内容不能为空")
    @Size(min=2, max=500)
    @Column(nullable = false) // 映射为字段，值不能为空
    private String content;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable = false) // 映射为字段，值不能为空
    @org.hibernate.annotations.CreationTimestamp  // 由数据库自动创建时间
    private Timestamp createTime;


}
