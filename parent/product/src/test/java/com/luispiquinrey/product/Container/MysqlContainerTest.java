package com.luispiquinrey.product.Container;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

@ActiveProfiles("dev")
@Testcontainers
@SpringBootTest
public class MysqlContainerTest {

}
