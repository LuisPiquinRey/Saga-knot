package com.luispiquinrey.product.Configuration;

import com.luispiquinrey.product.Command.*;
import com.luispiquinrey.product.Entities.Category;
import com.luispiquinrey.product.Repository.RepositoryBrandLookup;
import com.luispiquinrey.product.Repository.RepositoryCategoryLookup;
import com.luispiquinrey.product.Repository.RepositoryProductLookup;
import com.luispiquinrey.product.Service.BrandService;
import com.luispiquinrey.product.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final BrandService brandService;
    private final CategoryService categoryService;
    private final RepositoryProductLookup repositoryProductLookup;
    private final RepositoryBrandLookup repositoryBrandLookup;
    private final RepositoryCategoryLookup repositoryCategoryLookup;

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
            @NotNull List<? extends CommandMessage<?>> messages) {

        return (index, command) -> {
            Object payload = command.getPayload();

            if (payload instanceof CreateProductCommand createCmd) {
                if (!brandService.existsById(createCmd.getBrand().getId())) {
                    throw new IllegalArgumentException("Brand does not exist");
                }
                for (Category c : createCmd.getCategories()) {
                    if (!categoryService.existsById(c.getId())) {
                        throw new IllegalArgumentException("Category " + c.getId() + " does not exist");
                    }
                }
                if (repositoryProductLookup.existsById(createCmd.getIdProduct())) {
                    throw new IllegalArgumentException("Product with the same ID already exists");
                }
            } else if (payload instanceof UpdateProductCommand updateCmd) {
                if (!brandService.existsById(updateCmd.getBrand().getId())) {
                    throw new IllegalArgumentException("Brand does not exist");
                }
                for (Category c : updateCmd.getCategories()) {
                    if (!categoryService.existsById(c.getId())) {
                        throw new IllegalArgumentException("Category " + c.getId() + " does not exist");
                    }
                }
                if (!repositoryProductLookup.existsById(updateCmd.getIdProduct())) {
                    throw new IllegalArgumentException("Product does not exist");
                }
            } else if (payload instanceof DeleteProductCommand deleteCmd) {
                if (!repositoryProductLookup.existsById(deleteCmd.getIdProduct())) {
                    throw new IllegalArgumentException("Product does not exist");
                }
            } else if (payload instanceof CreateCategoryCommand createCat) {
                if (repositoryCategoryLookup.existsById(createCat.getIdCategory())) {
                    throw new IllegalArgumentException("Category with the same ID already exists");
                }
            } else if (payload instanceof UpdateCategoryCommand updateCat) {
                if (!repositoryCategoryLookup.existsById(updateCat.getIdCategory())) {
                    throw new IllegalArgumentException("Category does not exist");
                }
            } else if (payload instanceof DeleteCategoryCommand deleteCat) {
                if (!repositoryCategoryLookup.existsById(deleteCat.getIdCategory())) {
                    throw new IllegalArgumentException("Category does not exist");
                }
            } else if (payload instanceof CreateBrandCommand createBrand) {
                if (repositoryBrandLookup.existsById(createBrand.getIdBrand())) {
                    throw new IllegalArgumentException("Brand with the same ID already exists");
                }
            } else if (payload instanceof UpdateBrandCommand updateBrand) {
                if (!repositoryBrandLookup.existsById(updateBrand.getIdBrand())) {
                    throw new IllegalArgumentException("Brand does not exist");
                }
            } else if (payload instanceof DeleteBrandCommand deleteBrand) {
                if (!repositoryBrandLookup.existsById(deleteBrand.getIdBrand())) {
                    throw new IllegalArgumentException("Brand does not exist");
                }
            } else {
                log.debug("No interception for command type: {}", payload.getClass().getSimpleName());
            }

            return command;
        };
    }
}
