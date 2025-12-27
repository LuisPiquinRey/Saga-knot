package com.luispiquinrey.product.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import com.luispiquinrey.Error.SearchException;
import com.luispiquinrey.Service.CrudService;
import com.luispiquinrey.product.Command.CreateProductCommand;
import com.luispiquinrey.product.Command.UpdateProductCommand;
import com.luispiquinrey.product.DTO.ProductDto;
import com.luispiquinrey.product.Entities.Brand;
import com.luispiquinrey.product.Entities.Category;
import com.luispiquinrey.product.Entities.Gender;
import com.luispiquinrey.product.Entities.Product;
import com.luispiquinrey.product.Repository.RepositoryProduct;

@Service
public class ProductService extends CrudService<Product, String> {

    private final RepositoryProduct repositoryProduct;
    private final BrandService brandService;
    private final CategoryService categoryService;

    public ProductService(RepositoryProduct repositoryProduct,
                         BrandService brandService,
                         CategoryService categoryService) {
        super(repositoryProduct, Product.class);
        this.repositoryProduct = repositoryProduct;
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    public List<Product> findAll() {
        return repositoryProduct.findAll();
    }
}
