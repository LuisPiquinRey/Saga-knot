package com.luispiquinrey.product.Controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.luispiquinrey.product.Command.*;
import com.luispiquinrey.product.DTO.BrandDto;
import com.luispiquinrey.product.DTO.CategoryDto;
import com.luispiquinrey.product.DTO.ProductDto;
import com.luispiquinrey.product.Entities.Brand;
import com.luispiquinrey.product.Entities.Category;
import com.luispiquinrey.product.Entities.Gender;
import com.luispiquinrey.product.Entities.Product;
import com.luispiquinrey.product.Queries.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @Autowired
    public ProductController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }


    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody @Valid ProductDto productDto) {
        try {
            CreateProductCommand command = CreateProductCommand.builder()
                    .idProduct(UUID.randomUUID().toString())
                    .name(productDto.name())
                    .idBrand(productDto.idBrand())
                    .idCategories(productDto.idCategories())
                    .gender(productDto.genderName() != null ? new Gender(productDto.genderName()) : null)
                    .status(productDto.status())
                    .price(productDto.price())
                    .stock(productDto.stock())
                    .build();

            Object result = commandGateway.sendAndWait(command);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Product successfully created (id=" + result + ")");

        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .body("Product creation failed: " + ex.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable String id,
                                                @RequestBody @Valid ProductDto productDto) {
        try {
            UpdateProductCommand command = UpdateProductCommand.builder()
                    .idProduct(id)
                    .name(productDto.name())
                    .gender(productDto.genderName() != null ? new Gender(productDto.genderName()) : null)
                    .status(productDto.status())
                    .price(productDto.price())
                    .stock(productDto.stock())
                    .build();

            commandGateway.sendAndWait(command);
            return ResponseEntity.ok("Product successfully updated");

        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .body("Product update failed: " + ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        try {
            commandGateway.sendAndWait(
                    DeleteProductCommand.builder()
                            .idProduct(id)
                            .build()
            );
            return ResponseEntity.ok("Product successfully deleted");

        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .body("Product deletion failed: " + ex.getMessage());
        }
    }

    @PostMapping("/brand/create")
    public ResponseEntity<String> createBrand(@RequestBody @Valid BrandDto brandDto) {
        try {
            Object result = commandGateway.sendAndWait(
                    CreateBrandCommand.builder()
                            .idBrand(UUID.randomUUID().toString())
                            .name(brandDto.name())
                            .description(brandDto.description())
                            .build()
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Brand successfully created (id=" + result + ")");

        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .body("Brand creation failed: " + ex.getMessage());
        }
    }

    @PutMapping("/brand/update/{id}")
    public ResponseEntity<String> updateBrand(@PathVariable String id,
                                              @RequestBody @Valid BrandDto brandDto) {
        try {
            commandGateway.sendAndWait(
                    UpdateBrandCommand.builder()
                            .idBrand(id)
                            .name(brandDto.name())
                            .description(brandDto.description())
                            .build()
            );

            return ResponseEntity.ok("Brand successfully updated");

        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .body("Brand update failed: " + ex.getMessage());
        }
    }

    @PostMapping("/category/create")
    public ResponseEntity<String> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        try {
            Object result = commandGateway.sendAndWait(
                    CreateCategoryCommand.builder()
                            .idCategory(UUID.randomUUID().toString())
                            .name(categoryDto.name())
                            .description(categoryDto.description())
                            .image(categoryDto.image())
                            .build()
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Category successfully created (id=" + result + ")");

        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .body("Category creation failed: " + ex.getMessage());
        }
    }

    @PutMapping("/category/update/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable String id,
                                                 @RequestBody @Valid CategoryDto categoryDto) {
        try {
            commandGateway.sendAndWait(
                    UpdateCategoryCommand.builder()
                            .idCategory(id)
                            .name(categoryDto.name())
                            .description(categoryDto.description())
                            .image(categoryDto.image())
                            .build()
            );

            return ResponseEntity.ok("Category successfully updated");

        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .body("Category update failed: " + ex.getMessage());
        }
    }
}
