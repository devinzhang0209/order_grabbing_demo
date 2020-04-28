package com.devin.order.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Devin Zhang
 * @className Order
 * @description TODO
 * @date 2020/4/25 11:03
 */
@Data
@Table(name = "order_t")
public class Order implements Serializable {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer orderId;

    private String userId;
    private String productId;
    private Long createTime;

}
