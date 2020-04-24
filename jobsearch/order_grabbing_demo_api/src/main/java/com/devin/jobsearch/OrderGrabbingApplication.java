package com.devin.jobsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;


@MapperScan("com.devin.jobsearch.mapper")
@SpringBootApplication
public class OrderGrabbingApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderGrabbingApplication.class, args);
    }

}
