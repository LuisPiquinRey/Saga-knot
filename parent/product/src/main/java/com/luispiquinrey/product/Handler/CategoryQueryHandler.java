package com.luispiquinrey.product.Handler;

import java.util.List;
import java.util.Optional;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.product.Entities.Category;
import com.luispiquinrey.product.Queries.FindAllCategoriesQuery;
import com.luispiquinrey.product.Queries.FindCategoryByIdQuery;
import com.luispiquinrey.product.Repository.RepositoryCategory;

@Component
public class CategoryQueryHandler {

    private final RepositoryCategory repositoryCategory;

    @Autowired
    public CategoryQueryHandler(RepositoryCategory repositoryCategory) {
        this.repositoryCategory = repositoryCategory;
    }

    @QueryHandler
    public Optional<Category> handle(FindCategoryByIdQuery query) {
        return repositoryCategory.findById(query.getIdCategory());
    }

    @QueryHandler
    public List<Category> handle(FindAllCategoriesQuery query) {
        return repositoryCategory.findAll();
    }
}
