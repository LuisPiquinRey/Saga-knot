package com.luispiquinrey.product.Handler.Command;

import com.luispiquinrey.common.Utilities.DataService;
import com.luispiquinrey.common.Utilities.IDataService;
import com.luispiquinrey.common.Utilities.RedisDecorator;
import com.luispiquinrey.product.Entities.Brand;
import com.luispiquinrey.product.Entities.Category;
import com.luispiquinrey.product.Service.BrandService;
import com.luispiquinrey.product.Service.CategoryService;
import com.luispiquinrey.product.Service.ProductService;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.DisallowReplay;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.luispiquinrey.product.Entities.Product;
import com.luispiquinrey.product.Event.ProductCreatedEvent;
import com.luispiquinrey.product.Event.ProductDeletedEvent;
import com.luispiquinrey.product.Event.ProductUpdatedEvent;

import java.util.List;
import java.util.stream.Collectors;


@Component
@ProcessingGroup("product-collection")
@DisallowReplay
public class ProductCommandHandler {

    private static final Logger log = LoggerFactory.getLogger(ProductCommandHandler.class);

    private ProductService productService;
    private BrandService brandService;
    private CategoryService categoryService;
    private IDataService<Product,String> iDataService;
    private RedisDecorator<Product,String> redisDecorator;

    @Autowired
    public ProductCommandHandler(ProductService productService,
                                 BrandService brandService,
                                 CategoryService categoryService,
                                 @Qualifier(value = "redisTemplateProduct") RedisTemplate<String, Product> redisTemplate) {
        this.productService = productService;
        this.brandService = brandService;
        this.categoryService = categoryService;
        iDataService = new RedisDecorator<>(productService, redisTemplate, Product.class);
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent){
        log.info("Processing ProductCreatedEvent in CommandHandler - ID: {}", productCreatedEvent.getIdProduct());

        Product product = new Product();
        product.setId(productCreatedEvent.getIdProduct());
        product.setName(productCreatedEvent.getName());
        product.setPrice(productCreatedEvent.getPrice());
        product.setStock(productCreatedEvent.getStock());
        product.setGender(productCreatedEvent.getGender());
        product.setStatus(productCreatedEvent.getStatus());

        if (productCreatedEvent.getIdBrand() != null) {
            Brand brand = brandService.findTargetById(productCreatedEvent.getIdBrand())
                    .orElseThrow(() -> new IllegalArgumentException("Brand not found with ID: " + productCreatedEvent.getIdBrand()));
            product.setBrand(brand);
            log.debug("Brand mapped successfully: {}", brand.getId());
        }

        if (productCreatedEvent.getIdCategories() != null && !productCreatedEvent.getIdCategories().isEmpty()) {
            List<Category> categories = productCreatedEvent.getIdCategories().stream()
                    .map(categoryId -> categoryService.findTargetById(categoryId)
                            .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + categoryId)))
                    .collect(Collectors.toList());
            product.setCategories(categories);
            log.debug("Categories mapped successfully: {} categories", categories.size());
        }

        iDataService.createTarget(product);
        log.info("Product created successfully in CommandHandler - ID: {}", product.getId());
    }
    @EventHandler
    public void on(ProductUpdatedEvent event) {
        log.info("Processing ProductUpdatedEvent in CommandHandler - ID: {}", event.getIdProduct());

        Product product = new Product();
        product.setId(event.getIdProduct());
        product.setName(event.getName());
        product.setPrice(event.getPrice());
        product.setStock(event.getStock());
        product.setGender(event.getGender());
        product.setStatus(event.getStatus());

        iDataService.updateTarget(product);
        log.info("Product updated successfully in CommandHandler - ID: {}", product.getId());
    }

    @EventHandler
    public void on(ProductDeletedEvent productDeletedEvent){
        iDataService.deleteTarget(productDeletedEvent.getIdProduct());
    }

}
