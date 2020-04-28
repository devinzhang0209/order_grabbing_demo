package com.devin.order.config;

/**
 * @author Devin Zhang
 * @className RabbitConstants
 * @description TODO
 * @date 2020/4/25 10:11
 */

public class RabbitConstants {

    /**
     * 分列模式
     */
    public final static String FANOUT_MODE_QUEUE = "fanout.mode";

    /**
     * 日志打印队列
     */
    public final static String QUEUE_LOG_PRINT = "queue.log.recode";

    /**
     * 主题模式
     */
    public final static String TOPIC_MODE_QUEUE = "topic.mode";

    /**
     * 主题模式
     */
    public final static String TOPIC_ROUTING_KEY = "topic.*";

}
