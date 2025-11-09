package com.luispiquinrey.product.Configuration;

import java.util.List;

import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.luispiquinrey.product.Service.ProductService;

@Configuration
public class McpServerConfig {
    @Bean
    List<ToolCallback> toolCallbacks(ProductService productService){
        return List.of(ToolCallbacks.from(productService));
    }
}
