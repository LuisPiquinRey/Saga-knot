package com.luispiquinrey.order.Controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luispiquinrey.order.Command.CreateOrderCommand;

import com.luispiquinrey.order.Entities.Order;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder() {
        Order order = new Order();
        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .idOrder(order.getIdOrder()).build();
        try {
            String productId = commandGateway.sendAndWait(createOrderCommand);
            return ResponseEntity.ok(productId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating order: " + e.getMessage());
        }
    }

}
