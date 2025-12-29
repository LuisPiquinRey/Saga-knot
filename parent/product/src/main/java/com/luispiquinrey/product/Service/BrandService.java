package com.luispiquinrey.product.Service;

import org.springframework.stereotype.Service;

import com.luispiquinrey.common.Utilities.DataService;
import com.luispiquinrey.product.Entities.Brand;
import com.luispiquinrey.product.Repository.RepositoryBrand;

@Service
public class BrandService extends DataService<Brand, String> {

    private final RepositoryBrand repositoryBrand;

    public BrandService(RepositoryBrand repositoryBrand) {
        super(repositoryBrand, Brand.class);
        this.repositoryBrand = repositoryBrand;
    }

    public boolean existsById(String id) {
        return repositoryBrand.existsById(id);
    }
}