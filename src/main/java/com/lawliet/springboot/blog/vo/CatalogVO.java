package com.lawliet.springboot.blog.vo;

import com.lawliet.springboot.blog.domain.Catalog;

import java.io.Serializable;

/**
 * @author hao@lawliet.com
 * @since 2018/4/23 22:37
 */
public class CatalogVO implements Serializable{
    private static final long serialVersionUID = 1L;

    private String username;
    private Catalog catalog;

    public CatalogVO() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }
}
