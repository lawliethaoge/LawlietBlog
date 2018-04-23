package com.lawliet.springboot.blog.service;

import com.lawliet.springboot.blog.domain.Catalog;
import com.lawliet.springboot.blog.domain.User;
import com.lawliet.springboot.blog.respository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hao@lawliet.com
 * @since 2018/4/23 22:58
 */
@Service
public class CatalogServiceImpl implements CatalogService {
    private CatalogRepository catalogRepository;

    @Autowired
    public void setCatalogRepository(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @Override
    public Catalog saveCatalog(Catalog catalog) {
        // 判断重复
        List<Catalog> list = catalogRepository.findByUserAndName(catalog.getUser(), catalog.getName());
        if (list != null && list.size() > 0) {
            throw new IllegalArgumentException("该分类已经存在了");
        }
        return catalogRepository.save(catalog);
    }

    @Override
    public void removeCatalog(Long id) {
        catalogRepository.delete(id);

    }

    @Override
    public Catalog getCatalogById(Long id) {
        return catalogRepository.findOne(id);

    }

    @Override
    public List<Catalog> listCatalogs(User user) {
        return catalogRepository.findByUser(user);
    }
}
