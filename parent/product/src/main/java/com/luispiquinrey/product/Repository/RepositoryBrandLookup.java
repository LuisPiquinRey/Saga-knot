package com.luispiquinrey.product.Repository;

import com.luispiquinrey.product.Entities.Brand;
import com.luispiquinrey.product.Entities.Projection.BrandLookup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryBrandLookup extends JpaRepository<BrandLookup, String> {
}
