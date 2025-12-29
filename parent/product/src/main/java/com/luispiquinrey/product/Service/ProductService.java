package com.luispiquinrey.product.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.luispiquinrey.common.Service.DataService;
import com.luispiquinrey.product.Entities.Product;
import com.luispiquinrey.product.Repository.RepositoryProduct;

@Service
public class ProductService extends DataService<Product, String> {

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
