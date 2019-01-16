package com.duan.fantasies.dynamicdatasource;

import com.duan.fantasies.dynamicdatasource.config.datasource.DynamicDataSourceBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Created on 2018/9/25.
 *
 * @author DuanJiaNing
 */
@SpringBootApplication
@Import({DynamicDataSourceBuilder.class})
public class DynamicDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicDatasourceApplication.class, args);
    }

}
