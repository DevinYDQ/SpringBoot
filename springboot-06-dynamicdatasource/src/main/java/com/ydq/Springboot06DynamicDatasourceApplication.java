package com.ydq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Springboot06DynamicDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot06DynamicDatasourceApplication.class, args);
    }

}
