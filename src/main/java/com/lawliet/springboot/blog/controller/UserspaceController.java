package com.lawliet.springboot.blog.controller;

import com.lawliet.springboot.blog.domain.Blog;
import com.lawliet.springboot.blog.domain.Catalog;
import com.lawliet.springboot.blog.domain.User;
import com.lawliet.springboot.blog.domain.Vote;
import com.lawliet.springboot.blog.respository.CatalogRepository;
import com.lawliet.springboot.blog.service.BlogService;
import com.lawliet.springboot.blog.service.CatalogService;
import com.lawliet.springboot.blog.service.UserService;
import com.lawliet.springboot.blog.util.ConstraintViolationExceptionHandler;
import com.lawliet.springboot.blog.vo.Response;
import net.bytebuddy.asm.Advice;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

    private BlogService blogService;

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    private CatalogService catalogService;

    @Autowired
    public void setCatalogService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    /**
     * 获取某个用户的用户主页
     * @author hao
     * @param [username, model]
     * @return java.lang.String
     */
    @GetMapping("/{username}")
    public String userSpace(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        System.out.println("username" + username);
        return "redirect:/u/" + username + "/blogs";
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
        model.addAttribute("fileServerUrl", fileServerUrl);           //文件服务器地址返回给客户端
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
     *
     * @param [username, model]
     * @return org.springframework.web.servlet.ModelAndView
     * @author hao
     */
    @GetMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView avatar(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return new ModelAndView("userspace/avatar", "userModel", model);
    }

    /**
     * 保存头像
     *
     * @param [username, model]
     * @return org.springframework.web.servlet.ModelAndView
     * @author hao
     */
    @PostMapping("/{username}/avater")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> saveAvatar(@PathVariable("username") String username, @RequestBody User user) {
        String avatarUrl = user.getAvatar();
        User originUser = userService.findUserById(user.getId());
        if (avatarUrl.equals(originUser.getAvatar())) {                         //判断头像是否改变
            originUser.setAvatar(avatarUrl);
        }
        userService.saveOrUpdateUser(originUser);
        return ResponseEntity.ok().body(new Response(true, "处理成功", avatarUrl));


    }

   /**
    * 获取博客页面
    * @author hao
    * @param [username, order, catalogId, keyword, async, pageIndex, pageSize, model]
    * @return java.lang.String
    */
    @GetMapping("/{username}/blogs")
    public String listBlogsByOrder(@PathVariable("username") String username,
                                   @RequestParam(value = "order", required = false, defaultValue = "new") String order,
                                   @RequestParam(value = "catalog", required = false) Long catalogId,
                                   @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                   @RequestParam(value = "async", required = false) boolean async,
                                   @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                   Model model) {

        User user = (User) userDetailsService.loadUserByUsername(username);

        Page<Blog> page = null;

        if (catalogId != null && catalogId > 0) {
            Catalog catalog = catalogService.getCatalogById(catalogId);
            Pageable pageable = new PageRequest(pageIndex, pageSize);
            page = blogService.listBlogsByCatalog(catalog, pageable);  //通过分类查找博客
            order = "";

        } else if (order.equals("hot")) {   //最热查询
            Sort sort = new Sort(Sort.Direction.DESC,"readSize","commentSize","voteSize");
            Pageable pageable = new PageRequest(pageIndex,pageSize,sort);
            page = blogService.ListBlogsByTitleAndVote(user,keyword,pageable);
        }else if (order.equals("new")){    //最新查询
            Pageable pageable = new PageRequest(pageIndex,pageSize);
            page = blogService.ListBlogsByTitleAndTime(user,keyword,pageable);
        }
        List<Blog> blogs = page.getContent();                     //获取当前页面数据
        model.addAttribute("user", user);
        model.addAttribute("order", order);
        model.addAttribute("catalogId", catalogId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        model.addAttribute("blogList", blogs);
        return (async==true?"userspace/u :: #mainContainerRepleace":"userspace/u");

    }

    /**
     * 获取博客展示界面
     * @author hao
     * @param [username, id, model]
     * @return java.lang.String
     */
    @GetMapping("/{username}/blogs/{id}")
    public String listBlogsByOrder(@PathVariable("username") String username, @PathVariable("id")Long id,Model model) {
        User principal = null;
        Blog blog = blogService.getBlogById(id);

        //每次读取，阅读量加一
        blogService.ReadingIncrease(id);

        //判断操作用户是否是博客的所有者
        boolean isBlogOwner = false;
        if (SecurityContextHolder.getContext().getAuthentication() !=null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                &&  !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal !=null && username.equals(principal.getUsername())) {
                isBlogOwner = true;
            }
        }

        // 判断操作用户的点赞情况
        List<Vote> votes = blog.getVotes();
        Vote currentVote = null; // 当前用户的点赞情况

        if (principal !=null) {
            for (Vote vote : votes) {
                vote.getUser().getUsername().equals(principal.getUsername());
                currentVote = vote;
                break;
            }
        }

        model.addAttribute("isBlogOwner",isBlogOwner);
        model.addAttribute("blogModel",blog);
        model.addAttribute("currentVote",currentVote);

        System.out.print("blogId:" + id);
        return "userspace/blog";
    }

    /**
     * 获取新增页面
     * @author hao
     * @param [username, model]
     * @return org.springframework.web.servlet.ModelAndView
     */
    @GetMapping("/{username}/blogs/edit")
    public ModelAndView editBlog(@PathVariable("username")String username , Model model) {
        User user = (User)userDetailsService.loadUserByUsername(username);
        List<Catalog> catalogs = catalogService.listCatalogs(user);
        model.addAttribute("blog",new Blog(null,null,null));
        model.addAttribute("catalogs", catalogs);
        return new ModelAndView("userspace/blogedit","blogModel",model);
    }


    /**
     * 获取编辑博客的界面
     * @author hao
     * @param [username, id, model]
     * @return org.springframework.web.servlet.ModelAndView
     */
    @GetMapping("/{username}/blogs/edit/{id}")
    public ModelAndView editBlog(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
        // 获取用户分类列表
        User user = (User)userDetailsService.loadUserByUsername(username);
        List<Catalog> catalogs = catalogService.listCatalogs(user);


        model.addAttribute("catalogs", catalogs);
        model.addAttribute("blog", blogService.getBlogById(id));
        return new ModelAndView("userspace/blogedit", "blogModel", model);
    }



    /**
     * 新增保存博客
     * @author hao
     * @param [username, blog]
     * @return org.springframework.http.ResponseEntity<com.lawliet.springboot.blog.vo.Response>
     */
    @PostMapping("/{username}/blogs/edit")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> saveBlog(@PathVariable("username") String username, @RequestBody Blog blog) {
        // 对 Catalog 进行空处理
        if (blog.getCatalog().getId() == null) {

            User user = (User)userDetailsService.loadUserByUsername(username);
            blog.setUser(user);
            Catalog catalog = new Catalog(user,"默认分类");
            catalogService.saveCatalog(catalog);
            blog.setCatalog(catalog);
            blogService.saveBlog(blog);
            String redirectUrl = "/u/" + username + "/blogs/" + blog.getId();
            return ResponseEntity.ok().body(new Response(false,"未选择分类，为您放入默认分类",redirectUrl));
        }
        try {

            // 判断是修改还是新增

            if (blog.getId()!=null) {
                Blog orignalBlog2 = blogService.getBlogById(blog.getId());
                orignalBlog2.setTitle(blog.getTitle());
                orignalBlog2.setContent(blog.getContent());
                orignalBlog2.setSummary(blog.getSummary());
                orignalBlog2.setCatalog(blog.getCatalog());
                orignalBlog2.setTags(blog.getTags());

                blogService.saveBlog(orignalBlog2);  //保存
            } else {
                User user = (User)userDetailsService.loadUserByUsername(username);
                blog.setUser(user);
                blogService.saveBlog(blog);
            }

        } catch (javax.validation.ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        String redirectUrl = "/u/" + username + "/blogs/" + blog.getId();
        return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
    }

    /**
     * 删除博客
     * @author hao
     * @param [username, id]
     * @return org.springframework.http.ResponseEntity<com.lawliet.springboot.blog.vo.Response>
     */
    @DeleteMapping("/{username}/blogs/{id}")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> deleteBlog(@PathVariable("username") String username,@PathVariable("id") Long id) {

        try {
            blogService.removeBlog(id);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        String redirectUrl = "/u/" + username + "/blogs";
        return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
    }




}
