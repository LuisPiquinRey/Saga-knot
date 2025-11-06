package com.luispiquinrey.product.Service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.luispiquinrey.Service.CrudService;
import com.luispiquinrey.product.Entities.Category;
import com.luispiquinrey.product.Repository.RepositoryCategory;

@Service
public class CategoryService extends CrudService<Category,String>{

    public CategoryService(RepositoryCategory repositoryCategory) {
        super(repositoryCategory, Category.class);
    }
}