package com.devin.order.controller;

import com.devin.order.Service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Devin Zhang
 * @className JobController
 * @description TODO
 * @date 2020/4/22 16:36
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/addOrder")
    public String addOrder(String userId) {
        return orderService.insertOrder(userId);
    }

}
