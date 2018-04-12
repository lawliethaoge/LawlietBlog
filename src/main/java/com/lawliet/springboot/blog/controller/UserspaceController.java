package com.lawliet.springboot.blog.controller;

import com.lawliet.springboot.blog.domain.User;
import com.lawliet.springboot.blog.service.UserService;
import com.lawliet.springboot.blog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author hao@lawliet.com
 * @since 2018/3/16 18:20
 */

@Controller
@RequestMapping("/u")
public class UserspaceController {

    @Value("${file.server.url}")
    private String fileServerUrl;

    private UserDetailsService userDetailsService;

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public String userSpace(@PathVariable("username") String username) {
        System.out.println("username" + username);
        return "/userspace/u";
    }


    /**
     * 获取个人设置页面
     *
     * @param [username, model]
     * @return org.springframework.web.servlet.ModelAndView
     * @author hao
     */
    @GetMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView profile(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("fileServerUrl",fileServerUrl);           //文件服务器地址返回给客户端
        return new ModelAndView("userspace/profile", "userModel", model);

    }


    /**
     * 保存个人设置的修改
     *
     * @param [username, user]
     * @return java.lang.String
     * @author hao
     */
    @PostMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
    public String saveProfile(@PathVariable("username") String username, User user) {
        User originUser = userService.findUserById(user.getId());
        originUser.setEmail(user.getEmail());
        originUser.setName(user.getName());
        // 判断密码是否做了变更
        String rawPassword = originUser.getPassword();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePasswd = encoder.encode(user.getPassword());
        System.out.println("修改过密码：" + rawPassword + "    原始密码：" + encodePasswd);
        boolean isMatch = encoder.matches(rawPassword, encodePasswd);
        if (!isMatch) {
            originUser.setEncodePassword(user.getPassword());
        }

        userService.saveOrUpdateUser(originUser);                    //保存修改
        return "redirect:/u/" + username + "/profile";


    }


    /**
     * 获取保存头像界面
     * @author hao
     * @param [username, model]
     * @return org.springframework.web.servlet.ModelAndView
     */
    @GetMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView avatar(@PathVariable("username") String username, Model model) {
        User  user = (User)userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return new ModelAndView("/userspace/avatar", "userModel", model);
    }

    /**
     * 保存头像
     * @author hao
     * @param [username, model]
     * @return org.springframework.web.servlet.ModelAndView
     */
    @PostMapping("/{username}/avater")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> saveAvatar(@PathVariable("username") String username, @RequestBody User user) {
        String avatarUrl = user.getAvatar();
        User originUser = userService.findUserById(user.getId());
        if(avatarUrl.equals(originUser.getAvatar())){                         //判断头像是否改变
            originUser.setAvatar(avatarUrl);
        }
        userService.saveOrUpdateUser(originUser);
        return ResponseEntity.ok().body(new Response(true,"处理成功",avatarUrl));


    }

    /**
     * 获取博客页面
     *
     * @param [username, order, category, keyword]
     * @return java.lang.String
     * @author hao
     */
    @GetMapping("/{username}/blogs")
    public String listBlogsByOrder(@PathVariable("username") String username,
                                   @RequestParam(value = "order", required = false, defaultValue = "new") String order,
                                   @RequestParam(value = "category", required = false) Long category,
                                   @RequestParam(value = "keyword", required = false) String keyword) {

        if (category != null) {

            System.out.print("category:" + category);
            System.out.print("selflink:" + "redirect:/u/" + username + "/blogs?category=" + category);
            return "/u";

        } else if (keyword != null && keyword.isEmpty() == false) {

            System.out.print("keyword:" + keyword);
            System.out.print("selflink:" + "redirect:/u/" + username + "/blogs?keyword=" + keyword);
            return "/u";
        }

        System.out.print("order:" + order);
        System.out.print("selflink:" + "redirect:/u/" + username + "/blogs?order=" + order);
        return "/u";
    }

    @GetMapping("/{username}/blogs/{id}")
    public String listBlogsByOrder(@PathVariable("id") Long id) {

        System.out.print("blogId:" + id);
        return "/blog";
    }


    @GetMapping("/{username}/blogs/edit")
    public String editBlog() {

        return "/blogedit";
    }
}
