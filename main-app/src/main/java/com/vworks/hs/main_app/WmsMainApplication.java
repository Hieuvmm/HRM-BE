package com.vworks.hs.main_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.vworks.hs"})
@EnableJpaRepositories(basePackages = {"com.vworks.hs"})
@EntityScan(basePackages = {"com.vworks.hs"})
@ComponentScan(basePackages = {"com.vworks.hs"})
@EnableFeignClients(basePackages = {"com.vworks.hs"})
public class WmsMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(WmsMainApplication.class, args);
    }
}