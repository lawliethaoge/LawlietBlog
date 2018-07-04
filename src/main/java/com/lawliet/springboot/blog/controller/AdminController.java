package com.lawliet.springboot.blog.controller;

import com.lawliet.springboot.blog.vo.Menu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hao@lawliet.com
 * @since 2018/3/16 18:20
 */

@Controller
@RequestMapping("/admins")
public class AdminController {

    /**
     * 获取后台管理主页面
     * @author hao
     * @date 2018/3/24 16:01
     * @param [model]
     * @return org.springframework.web.servlet.ModelAndView
     */
    @GetMapping
    public ModelAndView listUsers(Model model) {
        List<Menu> list = new ArrayList<>();
        list.add(new Menu("用户管理","/users"));
        list.add(new Menu("博客管理","/blog"));
        model.addAttribute("menulist", list);
        System.out.println("haha");
        return new ModelAndView("admins/index", "model", model);
    }
}
