package com.luispiquinrey.product.Service;

import com.luispiquinrey.Service.GenericCrudService;
import com.luispiquinrey.product.Entities.Product;
import com.luispiquinrey.product.Repository.RepositoryProduct;

public class ImplServiceProduct extends GenericCrudService<Product, String>{

    public ImplServiceProduct(RepositoryProduct repositoryProduct) {
        super(repositoryProduct);
    }
    
}
