package com.luispiquinrey.product.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luispiquinrey.product.Command.CreateBrandCommand;
import com.luispiquinrey.product.Command.CreateCategoryCommand;
import com.luispiquinrey.product.Command.CreateProductCommand;
import com.luispiquinrey.product.Command.DeleteBrandCommand;
import com.luispiquinrey.product.Command.DeleteCategoryCommand;
import com.luispiquinrey.product.Command.DeleteProductCommand;
import com.luispiquinrey.product.Command.UpdateBrandCommand;
import com.luispiquinrey.product.Command.UpdateCategoryCommand;
import com.luispiquinrey.product.Command.UpdateProductCommand;
import com.luispiquinrey.product.DTO.BrandDto;
import com.luispiquinrey.product.DTO.CategoryDto;
import com.luispiquinrey.product.DTO.ProductDto;
import com.luispiquinrey.product.Entities.Brand;
import com.luispiquinrey.product.Entities.Category;
import com.luispiquinrey.product.Entities.Gender;
import com.luispiquinrey.product.Entities.Product;
import com.luispiquinrey.product.Queries.FindAllBrandsQuery;
import com.luispiquinrey.product.Queries.FindAllCategoriesQuery;
import com.luispiquinrey.product.Queries.FindAllProductsQuery;
import com.luispiquinrey.product.Queries.FindBrandByIdQuery;
import com.luispiquinrey.product.Queries.FindCategoryByIdQuery;
import com.luispiquinrey.product.Queries.FindProductByIdQuery;
import com.luispiquinrey.product.Repository.RepositoryBrand;
import com.luispiquinrey.product.Repository.RepositoryCategory;
import com.luispiquinrey.product.Service.BrandService;
import com.luispiquinrey.product.Service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;
    private final BrandService brandService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(CommandGateway commandGateway, QueryGateway queryGateway,
            BrandService brandService, CategoryService categoryService) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody @Valid ProductDto productDto) {
        try {
            Brand brand = null;
            if (productDto.idBrand() != null) {
                brand = brandService.findTargetById(productDto.idBrand())
                        .orElseThrow(() -> new RuntimeException(
                        "Brand not found with ID: " + productDto.idBrand()));
            }

            List<Category> categories = new ArrayList<>();
            if (productDto.categoryIds() != null && !productDto.categoryIds().isEmpty()) {
                for (String categoryId : productDto.categoryIds()) {
                    Category category = categoryService.findTargetById(categoryId)
                            .orElseThrow(() -> new RuntimeException(
                            "Category not found with ID: " + categoryId));
                    categories.add(category);
                }
            }

            Gender gender = null;
            if (productDto.genderName() != null) {
                gender = new Gender(productDto.genderName());
            }

            CreateProductCommand command = CreateProductCommand.builder()
                    .idProduct(UUID.randomUUID().toString())
                    .name(productDto.name())
                    .brand(brand)
                    .categories(categories)
                    .gender(gender)
                    .status(productDto.status())
                    .price(productDto.price())
                    .stock(productDto.stock())
                    .build();

            Object result = commandGateway.sendAndWait(command);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Product created with ID: " + result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating product: " + ex.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(
            @PathVariable String id,
            @RequestBody @Valid ProductDto productDto) {
        try {
            Brand brand = null;
            if (productDto.idBrand() != null) {
                brand = brandService.findTargetById(productDto.idBrand())
                        .orElseThrow(() -> new RuntimeException(
                        "Brand not found with ID: " + productDto.idBrand()));
            }

            List<Category> categories = new ArrayList<>();
            if (productDto.categoryIds() != null && !productDto.categoryIds().isEmpty()) {
                for (String categoryId : productDto.categoryIds()) {
                    Category category = categoryService.findTargetById(categoryId)
                            .orElseThrow(() -> new RuntimeException(
                            "Category not found with ID: " + categoryId));
                    categories.add(category);
                }
            }

            Gender gender = null;
            if (productDto.genderName() != null) {
                gender = new Gender(productDto.genderName());
            }

            UpdateProductCommand command = UpdateProductCommand.builder()
                    .idProduct(id)
                    .name(productDto.name())
                    .brand(brand)
                    .categories(categories)
                    .gender(gender)
                    .status(productDto.status())
                    .price(productDto.price())
                    .stock(productDto.stock())
                    .build();

            commandGateway.sendAndWait(command);
            return ResponseEntity.ok("Product updated successfully");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating product: " + ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        try {
            DeleteProductCommand command = DeleteProductCommand.builder()
                    .idProduct(id)
                    .build();

            commandGateway.sendAndWait(command);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting product: " + ex.getMessage());
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable String id) {
        try {
            FindProductByIdQuery query = FindProductByIdQuery.builder()
                    .idProduct(id)
                    .build();
            Optional<Product> result = queryGateway.query(query, Optional.class).join();

            if (result.isPresent()) {
                return ResponseEntity.ok(result.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllProducts() {
        try {
            FindAllProductsQuery query = FindAllProductsQuery.builder().build();
            List<Product> result = queryGateway.query(query, List.class).join();
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/brand/create")
    public ResponseEntity<String> createBrand(@RequestBody @Valid BrandDto brandDto) {
        try {
            CreateBrandCommand command = CreateBrandCommand.builder()
                    .idBrand(UUID.randomUUID().toString())
                    .name(brandDto.name())
                    .description(brandDto.description())
                    .build();

            Object result = commandGateway.sendAndWait(command);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Brand created with ID: " + result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating brand: " + ex.getMessage());
        }
    }

    @PutMapping("/brand/update/{id}")
    public ResponseEntity<String> updateBrand(
            @PathVariable String id,
            @RequestBody @Valid BrandDto brandDto) {
        try {
            UpdateBrandCommand command = UpdateBrandCommand.builder()
                    .idBrand(id)
                    .name(brandDto.name())
                    .description(brandDto.description())
                    .build();

            commandGateway.sendAndWait(command);
            return ResponseEntity.ok("Brand updated successfully");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating brand: " + ex.getMessage());
        }
    }

    @DeleteMapping("/brand/delete/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable String id) {
        try {
            DeleteBrandCommand command = DeleteBrandCommand.builder()
                    .idBrand(id)
                    .build();

            commandGateway.sendAndWait(command);
            return ResponseEntity.ok("Brand deleted successfully");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting brand: " + ex.getMessage());
        }
    }

    @GetMapping("/brand/find/{id}")
    public ResponseEntity<Brand> findBrandById(@PathVariable String id) {
        try {
            FindBrandByIdQuery query = FindBrandByIdQuery.builder()
                    .idBrand(id)
                    .build();
            Optional<Brand> result = queryGateway.query(query, Optional.class).join();

            if (result.isPresent()) {
                return ResponseEntity.ok(result.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/brand/all")
    public ResponseEntity<List<Brand>> findAllBrands() {
        try {
            FindAllBrandsQuery query = FindAllBrandsQuery.builder().build();
            List<Brand> result = queryGateway.query(query, List.class).join();
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/category/create")
    public ResponseEntity<String> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        try {
            CreateCategoryCommand command = CreateCategoryCommand.builder()
                    .idCategory(UUID.randomUUID().toString())
                    .name(categoryDto.name())
                    .description(categoryDto.description())
                    .image(categoryDto.image())
                    .build();

            Object result = commandGateway.sendAndWait(command);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Category created with ID: " + result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating category: " + ex.getMessage());
        }
    }

    @PutMapping("/category/update/{id}")
    public ResponseEntity<String> updateCategory(
            @PathVariable String id,
            @RequestBody @Valid CategoryDto categoryDto) {
        try {
            UpdateCategoryCommand command = UpdateCategoryCommand.builder()
                    .idCategory(id)
                    .name(categoryDto.name())
                    .description(categoryDto.description())
                    .image(categoryDto.image())
                    .build();

            commandGateway.sendAndWait(command);
            return ResponseEntity.ok("Category updated successfully");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating category: " + ex.getMessage());
        }
    }

    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable String id) {
        try {
            DeleteCategoryCommand command = DeleteCategoryCommand.builder()
                    .idCategory(id)
                    .build();

            commandGateway.sendAndWait(command);
            return ResponseEntity.ok("Category deleted successfully");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting category: " + ex.getMessage());
        }
    }

    @GetMapping("/category/find/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable String id) {
        try {
            FindCategoryByIdQuery query = FindCategoryByIdQuery.builder()
                    .idCategory(id)
                    .build();
            Optional<Category> result = queryGateway.query(query, Optional.class).join();

            if (result.isPresent()) {
                return ResponseEntity.ok(result.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/category/all")
    public ResponseEntity<List<Category>> findAllCategories() {
        try {
            FindAllCategoriesQuery query = FindAllCategoriesQuery.builder().build();
            List<Category> result = queryGateway.query(query, List.class).join();
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
