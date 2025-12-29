package com.luispiquinrey.product.Repository;

import com.luispiquinrey.product.Entities.Category;
import com.luispiquinrey.product.Entities.Projection.CategoryLookup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryCategoryLookup extends JpaRepository<CategoryLookup, String> {
}
