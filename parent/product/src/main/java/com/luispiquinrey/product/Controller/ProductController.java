package com.luispiquinrey.product.Controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private QueryGateway queryGateway;
/*
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductRequest productRequest, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        } else {
            try {
                Product product = new Product(productRequest.name(), productRequest.brand(), productRequest.price(), productRequest.stock());
                CreateProductCommand createProductCommand = CreateProductCommand.builder()
                        .idProduct(product.getIdProduct())
                        .brand(productRequest.brand())
                        .name(productRequest.name())
                        .price(productRequest.price())
                        .stock(productRequest.stock())
                        .build();

                String productId = commandGateway.sendAndWait(createProductCommand);

                return ResponseEntity.ok()
                        .body(Map.of(
                                "message", "Product created successfully",
                                "productId", productId
                        ));

            } catch (Exception e) {
                return ResponseEntity.internalServerError()
                        .body(Map.of("error", "Failed to create product: " + e.getMessage()));
            }
        }
    }
 */
}
