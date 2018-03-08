package com.lawliet.springboot.blog.com.lawliet.springboot.blog.repository.es;

import com.lawliet.springboot.blog.domain.EsBlog;
import com.lawliet.springboot.blog.respository.EsBlogRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author hao@lawliet.com
 * @since 2018/2/1 16:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsBlogRepositoryTest {
    @Autowired
    private EsBlogRepository esBlogRepository;

    @Before
    public void initRepositoryData() {
        esBlogRepository.deleteAll();
        esBlogRepository.save(new EsBlog("望庐山瀑布", "李白的望庐山瀑布", "哈哈", "日照香炉生紫烟，遥看瀑布挂前川。 飞流直下三千尺，疑是银河落九天。"));
        esBlogRepository.save(new EsBlog("早发白帝城", "李白的早发白帝城", "嘻嘻", "日照白帝彩云间，千里江陵一日还。 两岸猿声啼不住，轻舟已过万重山。"));
    }

    @Test
    public void testfindDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrContentContaining(){
        Pageable pageable = new PageRequest(0,20);
        String title = "望";
        String summary = "哈";
        String content = "日照";
        Page<EsBlog> page = esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining(title,summary,content,pageable);
        assertThat(page.getTotalElements()).isEqualTo(2);
        System.out.println("start-----------------");
        for(EsBlog blog : page.getContent()){
            System.out.println(blog.toString());
        }
        System.out.println("----------end 1");


        title = "早发";
        summary = "嘻嘻";
        content = "白帝";
        page = esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining(title,summary,content,pageable);
        assertThat(page.getTotalElements()).isEqualTo(1);
        System.out.println("start 2-----------------");
        for(EsBlog blog : page.getContent()){
            System.out.println(blog.toString());
        }
        System.out.println("----------end 2");

    }





}
