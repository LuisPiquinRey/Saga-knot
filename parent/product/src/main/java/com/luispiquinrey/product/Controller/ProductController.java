package com.luispiquinrey.product.Controller;

import java.util.HashMap;
import java.util.Map;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luispiquinrey.Enums.Status;
import com.luispiquinrey.product.Command.CreateProductCommand;
import com.luispiquinrey.product.DTO.ProductRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private CommandGateway commandGateway; 
    
    @Autowired
    private QueryGateway queryGateway; 

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
                CreateProductCommand createProductCommand = CreateProductCommand.builder()
                    .brand(productRequest.brand())
                    .name(productRequest.name())
                    .status(Status.BOUGHT)
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
}