package com.luispiquinrey.product.Handler;

import java.util.List;
import java.util.Optional;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luispiquinrey.product.Entities.Product;
import com.luispiquinrey.product.Queries.FindAllProductsQuery;
import com.luispiquinrey.product.Queries.FindProductByIdQuery;
import com.luispiquinrey.product.Repository.RepositoryProduct;

@Component
public class ProductQueryHandler {

    private final RepositoryProduct repositoryProduct;

    @Autowired
    public ProductQueryHandler(RepositoryProduct repositoryProduct) {
        this.repositoryProduct = repositoryProduct;
    }

    @QueryHandler
    public Optional<Product> handle(FindProductByIdQuery query) {
        return repositoryProduct.findById(query.getIdProduct());
    }

    @QueryHandler
    public List<Product> handle(FindAllProductsQuery query) {
        return repositoryProduct.findAll();
    }
}
