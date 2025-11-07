package com.luispiquinrey.cart.Configuration.OpenFeign;

import org.apache.hc.client5.http.impl.classic.NullBackoffStrategy;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="client-feign-product-validator",url = "http://localhost:8071/product")
public interface ProductExistenceValidator {
    @GetMapping("/exists/{idProduct}")
    boolean existsProduct(@PathVariable("idProduct") String idProduct);
}
