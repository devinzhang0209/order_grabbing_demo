package com.devin.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;


@MapperScan("com.devin.Order.mapper")
@SpringBootApplication
public class OrderGrabbingApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderGrabbingApplication.class, args);
    }

}
