package com.duan.fantasies.quartzjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuartzJobApplication {

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(QuartzJobApplication.class);
        application.run(args);

    }

}
