package com.lawliet.springboot.blog.controller;

import com.lawliet.springboot.blog.domain.Authority;
import com.lawliet.springboot.blog.domain.Blog;
import com.lawliet.springboot.blog.domain.User;
import com.lawliet.springboot.blog.service.AuthorityService;
import com.lawliet.springboot.blog.service.BlogService;
import com.lawliet.springboot.blog.service.UserService;
import com.lawliet.springboot.blog.util.ConstraintViolationExceptionHandler;
import com.lawliet.springboot.blog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hao@lawliet.com
 * @since 2018/5/31 16:46
 */

@Controller
@RequestMapping("/blog")
//@PreAuthorize("hasAuthority('ROLE_ADMIN')")  // 指定角色权限才能操作方法
public class AdminBlogController {
    private BlogService blogService;

    private AuthorityService authorityService;

    @Autowired
    public void setAuthorityService(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    /**
     * 查询所用用户
     */
    @GetMapping
    public ModelAndView list(@RequestParam(value = "async", required = false, defaultValue = "false") boolean async,
                             @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                             @RequestParam(value = "name", required = false, defaultValue = "") String name,
                             Model model) {

        Pageable pageable = new PageRequest(pageIndex, pageSize);
        Page<Blog> page = blogService.ListBlogsByTitle(name, pageable);
        List<Blog> list = page.getContent();    // 当前所在页面数据列表

        model.addAttribute("page", page);
        model.addAttribute("blogList", list);
        if (async == true) return new ModelAndView("blogs/list :: #mainContainerRepleace", "blogModel", model);
        else return new ModelAndView("blogs/list", "blogsModel", model);
    }


    /**
     * 获取表单页面
     *
     * @param [model]
     * @return org.springframework.web.servlet.ModelAndView
     * @author hao
     */
//    @GetMapping("/add")
//    public ModelAndView createForm(Model model) {
//        model.addAttribute("user", new User(null, null, null, null));
//        return new ModelAndView("users/add", "userModel", model);
//
//    }

    /**
     * 保存或修改用户
     *
     * @param [user, authorityId]
     * @return org.springframework.http.ResponseEntity<com.lawliet.springboot.blog.vo.Response>
     * @author hao
     */
    @PostMapping
    public ResponseEntity<Response> saveOrUpdateUser(Blog blog){
        System.out.println(blog.getId());
        try{
            blogService.saveBlog(blog);
        }catch (javax.validation.ConstraintViolationException e){
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }
        return  ResponseEntity.ok().body(new Response(true, "处理成功", blog) );


    }


    /**
     * 删除博客
     *
     * @param [id, model]
     * @return org.springframework.http.ResponseEntity<com.lawliet.springboot.blog.vo.Response>
     * @author hao
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id, Model model) {
        try {
            blogService.removeBlog(id);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "处理成功"));
    }


    /**
     * 获取修改用户的界面，数据
     *
     * @param [id, model]
     * @return org.springframework.web.servlet.ModelAndView
     * @author hao
     */
    @GetMapping(value = "edit/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Long id, Model model) {
        Blog blog = blogService.getBlogById(id);
        model.addAttribute("blog", blog);
        return new ModelAndView("blogs/edit", "blogModel", model);
    }
}
