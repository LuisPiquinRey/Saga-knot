package com.luispiquinrey.cart.Controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luispiquinrey.cart.Command.CreateCartCommand;

import com.luispiquinrey.cart.Entities.Cart;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/order")
public class CartController {

    @Autowired
    private CommandGateway commandGateway;

}
