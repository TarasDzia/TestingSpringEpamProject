package com.epam.spring.testingapp;

import io.swagger.annotations.SwaggerDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@SpringBootApplication
@EnableSwagger2
@EnableWebMvc
public class TestingAppSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestingAppSpringApplication.class, args);
    }
}
