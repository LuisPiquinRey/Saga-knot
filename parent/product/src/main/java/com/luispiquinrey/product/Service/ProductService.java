package com.luispiquinrey.product.Service;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import com.luispiquinrey.Service.CrudService;
import com.luispiquinrey.product.Entities.Product;
import com.luispiquinrey.product.Repository.RepositoryProduct;

@Service
public class ProductService extends CrudService<Product, String> {

    private final RepositoryProduct repositoryProduct;

    public ProductService(RepositoryProduct repositoryProduct) {
        super(repositoryProduct, Product.class);
        this.repositoryProduct = repositoryProduct;
    }

    @Tool(
            name = "Get_All_Products",
            description = "Retrieve a list of all products from the database"
    )
    public List<Product> findAll() {
        return repositoryProduct.findAll();
    }
}
