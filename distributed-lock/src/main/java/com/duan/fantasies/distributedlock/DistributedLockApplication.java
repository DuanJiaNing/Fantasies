package com.duan.fantasies.distributedlock;

import com.duan.fantasies.distributedlock.config.ApplicationReadyListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DistributedLockApplication {

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(DistributedLockApplication.class);
        application.addListeners(new ApplicationReadyListener());
        application.run(args);

    }

}
