package com.luispiquinrey.user.Controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import com.luispiquinrey.user.Containers.MysqlTestContainer;
import com.luispiquinrey.user.Service.ServiceUser;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import(MysqlTestContainer.class)
public class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean 
    private ServiceUser serviceUser;

    @Test
    void testCreate() {
        
    }
}