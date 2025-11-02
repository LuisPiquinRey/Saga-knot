package com.luispiquinrey.product.Service;

import org.springframework.stereotype.Service;

import com.luispiquinrey.Service.CrudService;
import com.luispiquinrey.product.Entities.Product;
import com.luispiquinrey.product.Repository.RepositoryProduct;

@Service
public class ProductService extends CrudService<Product, String>{

    public ProductService(RepositoryProduct repositoryProduct, Class<Product> entityClass) {
        super(repositoryProduct, entityClass);
    }
}
