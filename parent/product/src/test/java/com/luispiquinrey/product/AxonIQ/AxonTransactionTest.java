package com.luispiquinrey.product.AxonIQ;

import com.luispiquinrey.Enums.StatusProduct;
import com.luispiquinrey.product.Aggregate.BrandAggregate;
import com.luispiquinrey.product.Aggregate.CategoryAggregate;
import com.luispiquinrey.product.Aggregate.ProductAggregate;
import com.luispiquinrey.product.Command.CreateBrandCommand;
import com.luispiquinrey.product.Command.UpdateBrandCommand;
import com.luispiquinrey.product.Command.DeleteBrandCommand;
import com.luispiquinrey.product.Command.CreateCategoryCommand;
import com.luispiquinrey.product.Command.UpdateCategoryCommand;
import com.luispiquinrey.product.Command.DeleteCategoryCommand;
import com.luispiquinrey.product.Command.CreateProductCommand;
import com.luispiquinrey.product.Command.UpdateProductCommand;
import com.luispiquinrey.product.Command.DeleteProductCommand;
import com.luispiquinrey.product.Entities.Brand;
import com.luispiquinrey.product.Entities.Category;
import com.luispiquinrey.product.Entities.Gender;
import com.luispiquinrey.product.Event.BrandCreatedEvent;
import com.luispiquinrey.product.Event.BrandUpdatedEvent;
import com.luispiquinrey.product.Event.BrandDeletedEvent;
import com.luispiquinrey.product.Event.CategoryCreatedEvent;
import com.luispiquinrey.product.Event.CategoryUpdatedEvent;
import com.luispiquinrey.product.Event.CategoryDeletedEvent;
import com.luispiquinrey.product.Event.ProductCreatedEvent;
import com.luispiquinrey.product.Event.ProductUpdatedEvent;
import com.luispiquinrey.product.Event.ProductDeletedEvent;
import com.luispiquinrey.product.Service.BrandService;
import com.luispiquinrey.product.Service.CategoryService;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AxonTransactionTest {

    private FixtureConfiguration<BrandAggregate> brandFixture;
    private FixtureConfiguration<CategoryAggregate> categoryFixture;
    private FixtureConfiguration<ProductAggregate> productFixture;

    private BrandService brandService;
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        brandFixture = new AggregateTestFixture<>(BrandAggregate.class);
        categoryFixture = new AggregateTestFixture<>(CategoryAggregate.class);
        productFixture = new AggregateTestFixture<>(ProductAggregate.class);

        brandService = mock(BrandService.class);
        categoryService = mock(CategoryService.class);

        productFixture.registerInjectableResource(brandService);
        productFixture.registerInjectableResource(categoryService);
    }
    @Test
    @DisplayName("Test Brand Creation - Command should produce BrandCreatedEvent")
    public void testCreateBrandTransaction() {
        String brandId = UUID.randomUUID().toString();

        CreateBrandCommand command = CreateBrandCommand.builder()
                .idBrand(brandId)
                .name("Nike")
                .description("Just Do It")
                .build();

        BrandCreatedEvent expectedEvent = BrandCreatedEvent.builder()
                .idBrand(brandId)
                .name("Nike")
                .description("Just Do It")
                .build();

        brandFixture.givenNoPriorActivity()
                .when(command)
                .expectEvents(expectedEvent);
    }

    @Test
    @DisplayName("Test Brand Update - Command should produce BrandUpdatedEvent")
    public void testUpdateBrandTransaction() {
        String brandId = UUID.randomUUID().toString();

        BrandCreatedEvent createdEvent = BrandCreatedEvent.builder()
                .idBrand(brandId)
                .name("Nike")
                .description("Just Do It")
                .build();

        UpdateBrandCommand updateCommand = UpdateBrandCommand.builder()
                .idBrand(brandId)
                .name("Nike Pro")
                .description("Professional Sports Brand")
                .build();

        BrandUpdatedEvent expectedEvent = BrandUpdatedEvent.builder()
                .idBrand(brandId)
                .name("Nike Pro")
                .description("Professional Sports Brand")
                .build();

        brandFixture.given(createdEvent)
                .when(updateCommand)
                .expectEvents(expectedEvent);
    }

    @Test
    @DisplayName("Test Brand Deletion - Command should produce BrandDeletedEvent and mark aggregate as deleted")
    public void testDeleteBrandTransaction() {
        String brandId = UUID.randomUUID().toString();

        BrandCreatedEvent createdEvent = BrandCreatedEvent.builder()
                .idBrand(brandId)
                .name("Nike")
                .description("Just Do It")
                .build();

        DeleteBrandCommand deleteCommand = DeleteBrandCommand.builder()
                .idBrand(brandId)
                .build();

        BrandDeletedEvent expectedEvent = BrandDeletedEvent.builder()
                .idBrand(brandId)
                .build();

        brandFixture.given(createdEvent)
                .when(deleteCommand)
                .expectEvents(expectedEvent)
                .expectMarkedDeleted();
    }
    @Test
    @DisplayName("Test Category Creation - Command should produce CategoryCreatedEvent")
    public void testCreateCategoryTransaction() {
        String categoryId = UUID.randomUUID().toString();

        CreateCategoryCommand command = CreateCategoryCommand.builder()
                .idCategory(categoryId)
                .name("Running Shoes")
                .description("Shoes for running")
                .image("https://example.com/running-shoes.jpg")
                .build();

        CategoryCreatedEvent expectedEvent = CategoryCreatedEvent.builder()
                .idCategory(categoryId)
                .name("Running Shoes")
                .description("Shoes for running")
                .image("https://example.com/running-shoes.jpg")
                .build();

        categoryFixture.givenNoPriorActivity()
                .when(command)
                .expectEvents(expectedEvent);
    }

    @Test
    @DisplayName("Test Category Update - Command should produce CategoryUpdatedEvent")
    public void testUpdateCategoryTransaction() {
        String categoryId = UUID.randomUUID().toString();

        CategoryCreatedEvent createdEvent = CategoryCreatedEvent.builder()
                .idCategory(categoryId)
                .name("Running Shoes")
                .description("Shoes for running")
                .image("https://example.com/running-shoes.jpg")
                .build();

        UpdateCategoryCommand updateCommand = UpdateCategoryCommand.builder()
                .idCategory(categoryId)
                .name("Professional Running Shoes")
                .description("High-performance running shoes")
                .image("https://example.com/pro-running-shoes.jpg")
                .build();

        CategoryUpdatedEvent expectedEvent = CategoryUpdatedEvent.builder()
                .idCategory(categoryId)
                .name("Professional Running Shoes")
                .description("High-performance running shoes")
                .image("https://example.com/pro-running-shoes.jpg")
                .build();

        categoryFixture.given(createdEvent)
                .when(updateCommand)
                .expectEvents(expectedEvent);
    }

    @Test
    @DisplayName("Test Category Deletion - Command should produce CategoryDeletedEvent and mark aggregate as deleted")
    public void testDeleteCategoryTransaction() {
        String categoryId = UUID.randomUUID().toString();

        CategoryCreatedEvent createdEvent = CategoryCreatedEvent.builder()
                .idCategory(categoryId)
                .name("Running Shoes")
                .description("Shoes for running")
                .image("https://example.com/running-shoes.jpg")
                .build();

        DeleteCategoryCommand deleteCommand = DeleteCategoryCommand.builder()
                .idCategory(categoryId)
                .build();

        CategoryDeletedEvent expectedEvent = CategoryDeletedEvent.builder()
                .idCategory(categoryId)
                .build();

        categoryFixture.given(createdEvent)
                .when(deleteCommand)
                .expectEvents(expectedEvent)
                .expectMarkedDeleted();
    }

    @Test
    @DisplayName("Test Product Creation - Command should produce ProductCreatedEvent")
    public void testCreateProductTransaction() {
        String productId = UUID.randomUUID().toString();
        String brandId = UUID.randomUUID().toString();
        String categoryId = UUID.randomUUID().toString();

        Brand brand = new Brand("Nike", "Just Do It");
        brand.setId(brandId);

        Category category = new Category("Running Shoes", "Shoes for running", "image.jpg");
        category.setId(categoryId);

        Gender gender = new Gender("Unisex");

        when(brandService.existsById(brandId)).thenReturn(true);
        when(categoryService.existsById(categoryId)).thenReturn(true);

        CreateProductCommand command = CreateProductCommand.builder()
                .idProduct(productId)
                .name("Nike Air Max 2024")
                .brand(brand)
                .price(150.0f)
                .stock(100)
                .categories(Arrays.asList(category))
                .gender(gender)
                .status(StatusProduct.CREATED)
                .build();

        ProductCreatedEvent expectedEvent = ProductCreatedEvent.builder()
                .idProduct(productId)
                .name("Nike Air Max 2024")
                .brand(brand)
                .price(150.0f)
                .stock(100)
                .categories(Arrays.asList(category))
                .gender(gender)
                .status(StatusProduct.CREATED)
                .build();

        productFixture.givenNoPriorActivity()
                .when(command)
                .expectEvents(expectedEvent);
    }

    @Test
    @DisplayName("Test Product Creation with Non-Existent Brand - Should throw IllegalArgumentException")
    public void testCreateProductWithInvalidBrand() {
        String productId = UUID.randomUUID().toString();
        String brandId = UUID.randomUUID().toString();
        String categoryId = UUID.randomUUID().toString();

        Brand brand = new Brand("Nike", "Just Do It");
        brand.setId(brandId);

        Category category = new Category("Running Shoes", "Shoes for running", "image.jpg");
        category.setId(categoryId);

        Gender gender = new Gender("Unisex");

        when(brandService.existsById(brandId)).thenReturn(false);
        when(categoryService.existsById(categoryId)).thenReturn(true);

        CreateProductCommand command = CreateProductCommand.builder()
                .idProduct(productId)
                .name("Nike Air Max 2024")
                .brand(brand)
                .price(150.0f)
                .stock(100)
                .categories(Arrays.asList(category))
                .gender(gender)
                .status(StatusProduct.CREATED)
                .build();

        productFixture.givenNoPriorActivity()
                .when(command)
                .expectException(IllegalArgumentException.class)
                .expectExceptionMessage("Brand does not exist");
    }

    @Test
    @DisplayName("Test Product Creation with Non-Existent Category - Should throw IllegalArgumentException")
    public void testCreateProductWithInvalidCategory() {
        String productId = UUID.randomUUID().toString();
        String brandId = UUID.randomUUID().toString();
        String categoryId = UUID.randomUUID().toString();

        Brand brand = new Brand("Nike", "Just Do It");
        brand.setId(brandId);

        Category category = new Category("Running Shoes", "Shoes for running", "image.jpg");
        category.setId(categoryId);

        Gender gender = new Gender("Unisex");

        when(brandService.existsById(brandId)).thenReturn(true);
        when(categoryService.existsById(categoryId)).thenReturn(false);

        CreateProductCommand command = CreateProductCommand.builder()
                .idProduct(productId)
                .name("Nike Air Max 2024")
                .brand(brand)
                .price(150.0f)
                .stock(100)
                .categories(Arrays.asList(category))
                .gender(gender)
                .status(StatusProduct.CREATED)
                .build();

        productFixture.givenNoPriorActivity()
                .when(command)
                .expectException(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Test Product Update - Command should produce ProductUpdatedEvent")
    public void testUpdateProductTransaction() {
        String productId = UUID.randomUUID().toString();
        String brandId = UUID.randomUUID().toString();
        String categoryId = UUID.randomUUID().toString();

        Brand brand = new Brand("Nike", "Just Do It");
        brand.setId(brandId);

        Category category = new Category("Running Shoes", "Shoes for running", "image.jpg");
        category.setId(categoryId);

        Gender gender = new Gender("Unisex");

        when(brandService.existsById(brandId)).thenReturn(true);
        when(categoryService.existsById(categoryId)).thenReturn(true);

        ProductCreatedEvent createdEvent = ProductCreatedEvent.builder()
                .idProduct(productId)
                .name("Nike Air Max 2024")
                .brand(brand)
                .price(150.0f)
                .stock(100)
                .categories(Arrays.asList(category))
                .gender(gender)
                .status(StatusProduct.CREATED)
                .build();

        UpdateProductCommand updateCommand = UpdateProductCommand.builder()
                .idProduct(productId)
                .name("Nike Air Max 2024 Pro")
                .brand(brand)
                .price(180.0f)
                .stock(80)
                .categories(Arrays.asList(category))
                .gender(gender)
                .status(StatusProduct.CREATED)
                .build();

        ProductUpdatedEvent expectedEvent = ProductUpdatedEvent.builder()
                .idProduct(productId)
                .name("Nike Air Max 2024 Pro")
                .brand(brand)
                .price(180.0f)
                .stock(80)
                .categories(Arrays.asList(category))
                .gender(gender)
                .status(StatusProduct.CREATED)
                .build();

        productFixture.given(createdEvent)
                .when(updateCommand)
                .expectEvents(expectedEvent);
    }

    @Test
    @DisplayName("Test Product Deletion - Command should produce ProductDeletedEvent and mark aggregate as deleted")
    public void testDeleteProductTransaction() {
        String productId = UUID.randomUUID().toString();
        String brandId = UUID.randomUUID().toString();
        String categoryId = UUID.randomUUID().toString();

        Brand brand = new Brand("Nike", "Just Do It");
        brand.setId(brandId);

        Category category = new Category("Running Shoes", "Shoes for running", "image.jpg");
        category.setId(categoryId);

        Gender gender = new Gender("Unisex");

        ProductCreatedEvent createdEvent = ProductCreatedEvent.builder()
                .idProduct(productId)
                .name("Nike Air Max 2024")
                .brand(brand)
                .price(150.0f)
                .stock(100)
                .categories(Arrays.asList(category))
                .gender(gender)
                .status(StatusProduct.CREATED)
                .build();

        DeleteProductCommand deleteCommand = DeleteProductCommand.builder()
                .idProduct(productId)
                .build();

        ProductDeletedEvent expectedEvent = ProductDeletedEvent.builder()
                .idProduct(productId)
                .build();

        productFixture.given(createdEvent)
                .when(deleteCommand)
                .expectEvents(expectedEvent)
                .expectMarkedDeleted();
    }

    @Test
    @DisplayName("Test Product Update with Invalid Brand - Should throw IllegalArgumentException")
    public void testUpdateProductWithInvalidBrand() {
        String productId = UUID.randomUUID().toString();
        String brandId = UUID.randomUUID().toString();
        String newBrandId = UUID.randomUUID().toString();
        String categoryId = UUID.randomUUID().toString();

        Brand brand = new Brand("Nike", "Just Do It");
        brand.setId(brandId);

        Brand newBrand = new Brand("Adidas", "Impossible is Nothing");
        newBrand.setId(newBrandId);

        Category category = new Category("Running Shoes", "Shoes for running", "image.jpg");
        category.setId(categoryId);

        Gender gender = new Gender("Unisex");

        ProductCreatedEvent createdEvent = ProductCreatedEvent.builder()
                .idProduct(productId)
                .name("Nike Air Max 2024")
                .brand(brand)
                .price(150.0f)
                .stock(100)
                .categories(Arrays.asList(category))
                .gender(gender)
                .status(StatusProduct.CREATED)
                .build();

        when(brandService.existsById(newBrandId)).thenReturn(false);
        when(categoryService.existsById(categoryId)).thenReturn(true);

        UpdateProductCommand updateCommand = UpdateProductCommand.builder()
                .idProduct(productId)
                .name("Adidas Ultra Boost")
                .brand(newBrand)
                .price(180.0f)
                .stock(80)
                .categories(Arrays.asList(category))
                .gender(gender)
                .status(StatusProduct.CREATED)
                .build();

        productFixture.given(createdEvent)
                .when(updateCommand)
                .expectException(IllegalArgumentException.class)
                .expectExceptionMessage("Brand does not exist");
    }
}
