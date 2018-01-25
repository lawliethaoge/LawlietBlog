package com.lawliet.springboot.blog.controller;

import com.lawliet.springboot.blog.domain.User;
import com.lawliet.springboot.blog.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Scanner;

/**
 * @author hao@lawliet.com
 * @since 2018/1/22 21:46
 */
//User控制器
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    //查询所有用户
    public ModelAndView list(Model model) {
        model.addAttribute("userList", userRepository.findAll());
        model.addAttribute("title", "用户管理");
        return new ModelAndView("users/list", "userModel", model);
    }

    @GetMapping("{id}")
    //查询用户
    public ModelAndView view(@PathVariable("id")Long id,Model model) {
        User user = userRepository.findOne(id);
        model.addAttribute("user", user);
        model.addAttribute("title","查看用户");
        return new ModelAndView("users/view", "userModel", model);
    }


    //获取创建表单页面
    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("user",new User(0,null,null));
        model.addAttribute("title","创建用户");
        return new ModelAndView("users/form", "userModel", model);

    }

    @PostMapping
    public ModelAndView saveOrUpdateUser(User user){
        user = userRepository.save(user);
        return new ModelAndView("redirect:/users");

    }


}
