package com.duan.fantasies.shm.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created on 2018/4/24.
 *
 * @author 段佳宁
 */
@SpringBootApplication
@ComponentScan("com.duan.fantasies.shm")
public class SHMLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SHMLogApplication.class, args);
    }

}
