package com.luispiquinrey.product.Handler;

import java.util.List;
import java.util.Optional;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.product.Entities.Brand;
import com.luispiquinrey.product.Queries.FindAllBrandsQuery;
import com.luispiquinrey.product.Queries.FindBrandByIdQuery;
import com.luispiquinrey.product.Repository.RepositoryBrand;

@Component
public class BrandQueryHandler {

    private final RepositoryBrand repositoryBrand;

    @Autowired
    public BrandQueryHandler(RepositoryBrand repositoryBrand) {
        this.repositoryBrand = repositoryBrand;
    }

    @QueryHandler
    public Optional<Brand> handle(FindBrandByIdQuery query) {
        return repositoryBrand.findById(query.getIdBrand());
    }

    @QueryHandler
    public List<Brand> handle(FindAllBrandsQuery query) {
        return repositoryBrand.findAll();
    }
}
