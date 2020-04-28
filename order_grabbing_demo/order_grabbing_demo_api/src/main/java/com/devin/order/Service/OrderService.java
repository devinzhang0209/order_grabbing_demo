package com.devin.order.Service;

import com.devin.order.config.RabbitConstants;
import com.devin.order.mapper.OrderMapper;
import com.devin.order.model.Order;
import com.devin.order.util.RedisClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author Devin Zhang
 * @className OrderService
 * @description TODO
 * @date 2020/4/25 11:14
 */

@Component
public class OrderService {

    public static final String PRODUCT_ID_KEY = "PID001_";
    private static final Integer PRODUCT_COUNT = 5000;

    private static final String HAS_BUY_USER_KEY = "HAS_BUY_USER_KEY_";

    private static final String LOCK_KEY = "LOCK_KEY_";


    private static final String FAIL_BUYED = "已经买过了";
    private static final String BUYE_SUCCESS = "购买成功";
    private static final String FAIL_SOLD_OUT = "没货了";
    private static final String FAIL_BUSY = "排队中，请重试！";

    @Resource
    private RedisClient redisClient;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;


    @PostConstruct
    public void initOrder() {
        redisClient.set(PRODUCT_ID_KEY, PRODUCT_COUNT);
        System.out.println("商品已经初始化完成：数量：" + PRODUCT_COUNT);
    }

    /**
     * 下单
     *
     * @param userId
     */
    public String insertOrder(String userId) {

        //判断用户是否已买
        Object hasBuy = redisClient.get(HAS_BUY_USER_KEY, userId);
        if (hasBuy != null) {
            return FAIL_BUYED;
        }

        //10s自动过期
        int redisExpireTime = 10 * 1000;
        long lockValue = System.currentTimeMillis() + redisExpireTime;
        //后去redis锁，只有获取成功才能继续操作
        boolean getLock = redisClient.lock(LOCK_KEY, String.valueOf(lockValue));
        System.out.println(userId + " getLock:" + getLock);
        if (getLock) {
            Integer productCount = (Integer) redisClient.get(PRODUCT_ID_KEY);
            System.out.println("productCount:" + productCount);
            //库存大于0才能继续下单
            if (productCount > 0) {

                rabbitTemplate.convertAndSend(RabbitConstants.TOPIC_ROUTING_KEY, userId);
                //减库存
                redisClient.set(PRODUCT_ID_KEY, (productCount - 1));
                //记录用户已买
                redisClient.set(HAS_BUY_USER_KEY, userId, "1");
                //手动释放锁
                redisClient.unlock(LOCK_KEY, String.valueOf(lockValue));
                return BUYE_SUCCESS;
            } else {
                System.out.println("亲，" + FAIL_SOLD_OUT);
                //手动释放锁
                redisClient.unlock(LOCK_KEY, String.valueOf(lockValue));
                return FAIL_SOLD_OUT;
            }
        } else {
            return FAIL_BUSY;
        }
    }


}
