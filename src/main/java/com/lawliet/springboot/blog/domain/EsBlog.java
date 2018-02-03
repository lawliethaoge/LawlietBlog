package com.lawliet.springboot.blog.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * Blog 文档
 *
 * @author hao@lawliet.com
 * @since 2018/2/1 15:46
 */

@Document(indexName = "blog",type ="blog")    //标识为文档
//实现可序列化接口
public class EsBlog implements Serializable {

    @Id  //主键
    private String id;
    private String title;
    private String summary;     //摘要
    private String content;     //正文内容

    protected EsBlog() {     //JPA 规范要求，防止直接使用
    }

    public EsBlog(String id, String title, String summary, String content) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "EsBlog{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
