package com.admin.tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AdminToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminToolApplication.class, args);
    }
}
