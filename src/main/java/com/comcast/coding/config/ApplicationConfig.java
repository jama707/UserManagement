package com.comcast.coding.config;

import com.comcast.coding.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Jama Asatillayev on 5/27/2015.
 */

@SpringBootApplication
@EnableTransactionManagement
@EnableAutoConfiguration
@EntityScan("com.comcast.coding.entity")
@ComponentScan("com.comcast.coding")
public class ApplicationConfig {

    public static void main(String[] args) {

        SpringApplication.run(ApplicationConfig.class, args);
    }


}
