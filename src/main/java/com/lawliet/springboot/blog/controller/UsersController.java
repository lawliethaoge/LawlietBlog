package com.lawliet.springboot.blog.controller;

import com.lawliet.springboot.blog.domain.Authority;
import com.lawliet.springboot.blog.domain.User;
import com.lawliet.springboot.blog.respository.AuthorityRepository;
import com.lawliet.springboot.blog.service.AuthorityService;
import com.lawliet.springboot.blog.service.UserService;
import com.lawliet.springboot.blog.util.ConstraintViolationExceptionHandler;
import com.lawliet.springboot.blog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;


/**
 * @author hao@lawliet.com
 * @since 2018/3/16 18:19
 */
@Controller
@RequestMapping("/users")
//@PreAuthorize("hasAuthority('ROLE_ADMIN')")  // 指定角色权限才能操作方法
public class UsersController {


    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private AuthorityService authorityService;

    @Autowired
    public void setAuthorityService(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    /**
     * 查询所用用户
     *
     */
    @GetMapping
    public ModelAndView list(@RequestParam(value="async",required=false,defaultValue = "false") boolean async,
                             @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
                             @RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize,
                             @RequestParam(value="name",required=false,defaultValue="") String name,
                             Model model) {

        Pageable pageable = new PageRequest(pageIndex, pageSize);
        Page<User> page = userService.listUsersByNameLike(name,pageable);
        List<User> list = page.getContent();	// 当前所在页面数据列表

        model.addAttribute("page", page);
        model.addAttribute("userList", list);
        if (async == true) return new ModelAndView("users/list :: #mainContainerRepleace", "userModel", model);
        else return new ModelAndView("users/list", "userModel", model);
    }


    /**
     * 获取表单页面
     * @author hao
     * @param [model]
     * @return org.springframework.web.servlet.ModelAndView
     */
    @GetMapping("/add")
    public ModelAndView createForm(Model model){
        model.addAttribute("user",new User(null,null,null,null));
        return new ModelAndView("users/add","userModel",model);

    }

    /**
     * 保存或修改用户
     * @author hao
     * @param [user, authorityId]
     * @return org.springframework.http.ResponseEntity<com.lawliet.springboot.blog.vo.Response>
     */
    @PostMapping
    public ResponseEntity<Response> saveOrUpdateUser(User user,Long authorityId){
        List<Authority> authorities = new ArrayList<>();                              //设置权限
        authorities.add(authorityService.getAuthorityById(authorityId));
        user.setAuthorities(authorities);

        if(user.getId() == null){
            user.setEncodePassword(user.getPassword());  //加密
        }else {
            User originalUser = userService.findUserById(user.getId());
            String rawPassword = originalUser.getPassword();
            PasswordEncoder encoder= new BCryptPasswordEncoder();
            String encodePasswd = encoder.encode(user.getPassword());
            boolean isMatch = encoder.matches(rawPassword,encodePasswd);
            if(!isMatch){
                user.setEncodePassword(user.getPassword());
            }else {
                user.setPassword(user.getPassword());
            }
        }

        try{
            userService.saveOrUpdateUser(user);
        }catch (javax.validation.ConstraintViolationException e){
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }
        return  ResponseEntity.ok().body(new Response(true, "处理成功", user) );


    }

    /**
     * 删除用户
     * @author hao
     * @param [id, model]
     * @return org.springframework.http.ResponseEntity<com.lawliet.springboot.blog.vo.Response>
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id, Model model) {
        try {
            userService.removeUser(id);
        } catch (Exception e) {
            return  ResponseEntity.ok().body( new Response(false, e.getMessage()));
        }
        return  ResponseEntity.ok().body( new Response(true, "处理成功"));
    }


    /**
     * 获取修改用户的界面，数据
     * @author hao
     * @param [id, model]
     * @return org.springframework.web.servlet.ModelAndView
     */
    @GetMapping(value = "edit/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return new ModelAndView("users/edit", "userModel", model);
    }
}
