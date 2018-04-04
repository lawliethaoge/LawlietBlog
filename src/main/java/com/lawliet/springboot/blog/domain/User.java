package com.lawliet.springboot.blog.domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author hao@lawliet.com
 * @since 2018/1/22 21:29
 */
@Entity//实体
public class User {
    @Id//主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //主键策略（自增）
    private Long id;  //唯一标识

    @NotEmpty(message = "名称不能为空")
    @Size(min=2,max = 20)
    @Column(nullable = false,length = 20)  //映射字段不能为空
    private String name;  //名称

    @NotEmpty(message = "用户名不能为空")
    @Size(min=2,max = 20)
    @Column(nullable = false,length = 20,unique = true)
    private String username;  //用户名


    @NotEmpty(message = "密码不能为空")
    @Size(min=6,max = 100)
    @Column(nullable = false,length = 100)
    private String password;  //密码

    @Column(length = 200)
    private String portrait;  //头像信息


    @NotEmpty(message = "邮箱不能为空")
    @Size(min=2,max = 50)
    @Email(message = "邮箱格式不正确")
    @Column(nullable = false,length = 50,unique = true)
    private String email;  //邮箱

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;


    protected  User(){  //有参/无参构造,设置成 protect 防止直接使用

    }


    public User(Long id,String name, String username,   String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
    }


    //???????????????????????????
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //  需将 List<Authority> 转成 List<SimpleGrantedAuthority>，否则前端拿不到角色列表名称
        List<SimpleGrantedAuthority> simpleAuthorities = new ArrayList<>();
        for(GrantedAuthority authority : this.authorities){
            simpleAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        return simpleAuthorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getEmail() {
        return email;
    }

    public void setEncodePassword(String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePasswd = encoder.encode(password);
        this.password = encodePasswd;
    }



    public void setEmail(String email) {

        this.email = email;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", portrait='" + portrait + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
