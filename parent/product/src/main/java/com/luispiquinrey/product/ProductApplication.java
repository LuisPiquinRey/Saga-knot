package com.luispiquinrey.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.luispiquinrey.common","com.luispiquinrey.product"})
@EnableJpaAuditing
@Slf4j
public class ProductApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        displayStartupInfo();
    }
    private void displayStartupInfo() {
        log.info("╔══════════════════════════════════════════════════════════════╗");
        log.info("║                                                              ║");
        log.info("║              🚀 PRODUCT MICROSERVICE STARTED 🚀             ║");
        log.info("║                                                              ║");
        log.info("║  ┌─────────────────────────────────────────────────────┐   ║");
        log.info("║  │  Service:     Product Management Service           │   ║");
        log.info("║  │  Framework:   Axon Framework (CQRS + Event Sourcing)│  ║");
        log.info("║  │  Database:    MySQL                                 │   ║");
        log.info("║  │  Version:     0.0.1-SNAPSHOT                        │   ║");
        log.info("║  └─────────────────────────────────────────────────────┘   ║");
        log.info("║                                                              ║");
        log.info("║  📦 Features:                                                ║");
        log.info("║     ✓ Event-Driven Architecture                             ║");
        log.info("║     ✓ CQRS Pattern Implementation                           ║");
        log.info("║     ✓ Service Discovery (Eureka)                            ║");
        log.info("║     ✓ Distributed Tracing (OpenTelemetry)                   ║");
        log.info("║     ✓ Health Monitoring (Actuator)                          ║");
        log.info("║                                                              ║");
        log.info("║  Ready to handle product commands and events! 💪            ║");
        log.info("║                                                              ║");
        log.info("╚══════════════════════════════════════════════════════════════╝");
    }
}