package com.devin.order.Service;

/**
 * 【消息队列处理器】
 *
 * @author Devin Zhang
 * @className RabbitMqHandler
 * @description TODO
 * @date 2020/4/25 10:54
 */


import com.devin.order.config.RabbitConstants;
import com.devin.order.mapper.OrderMapper;
import com.devin.order.model.Order;
import com.devin.order.util.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Slf4j
@Component
public class RabbitMqHandler {

    @Resource
    private RedisClient redisClient;

    @Resource
    private OrderMapper orderMapper;

    /**
     * 日志打印处理handler
     *
     * @param message 待处理的消息体
     */
    @RabbitListener(queues = RabbitConstants.QUEUE_LOG_PRINT)
    public void queueLogPrintHandler(String message) {
        log.info("接收到操作日志记录消息：[{}]", message);
    }

    /**
     * 主题模式处理handler
     *
     * @param message 待处理的消息体
     */
    @RabbitListener(queues = RabbitConstants.TOPIC_ROUTING_KEY)
    public void queueTopicHandler(String message) {
        log.info("主题模式处理器，接收消息：[{}]", message);

        //todo

        String userId = message;

        //产生订单
        System.out.println("userId:" + userId);
        Order order = new Order();
        order.setProductId(OrderService.PRODUCT_ID_KEY);
        order.setUserId(userId);
        order.setCreateTime(System.currentTimeMillis());
        orderMapper.insert(order);


        System.out.println("用户:" + userId + "下单成功");

    }

}
