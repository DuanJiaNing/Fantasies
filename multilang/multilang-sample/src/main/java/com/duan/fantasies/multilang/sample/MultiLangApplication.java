package com.duan.fantasies.multilang.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MultiLangApplication {

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(MultiLangApplication.class);
        application.run(args);

    }

}
