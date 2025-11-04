package com.luispiquinrey.product.Service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.luispiquinrey.Service.CrudService;
import com.luispiquinrey.product.Entities.Brand;
import com.luispiquinrey.product.Repository.RepositoryBrand;

@Service
public class BrandService extends CrudService<Brand,String>{

    public BrandService(RepositoryBrand repositoryBrand) {
        super(repositoryBrand, Brand.class);
    }
}