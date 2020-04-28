package com.devin.order.config;

/**
 * @author Devin Zhang
 * @className RabbitMqConfig
 * @description TODO
 * @date 2020/4/25 10:12
 */


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitMqConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> log.info("消息发送成功:correlationData[{}],ack[{}],cause[{}]", correlationData, ack, cause));
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> log.info("消息丢失:exchange[{}],route[{}],replyCode[{}],replyText[{}],message:{}", exchange, routingKey, replyCode, replyText, message));
        return rabbitTemplate;
    }

    /**
     * 日志打印队列
     */
    @Bean
    public Queue logPrintQueue() {
        return new Queue(RabbitConstants.QUEUE_LOG_PRINT);
    }

    /**
     * 分列模式队列
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitConstants.FANOUT_MODE_QUEUE);
    }

    /**
     * 分列模式绑定队列
     *
     * @param logPrintQueue  绑定队列
     * @param fanoutExchange 分列模式交换器
     */
    @Bean
    public Binding fanoutBinding(Queue logPrintQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(logPrintQueue).to(fanoutExchange);
    }

    /**
     * 主题队列
     */
    @Bean
    public Queue topicQueue() {
        return new Queue(RabbitConstants.TOPIC_ROUTING_KEY);
    }

    /**
     * 主题模式队列
     * <li>路由格式必须以 . 分隔，比如 user.email 或者 user.aaa.email</li>
     * <li>通配符 * ，代表一个占位符，或者说一个单词，比如路由为 user.*，那么 user.email 可以匹配，但是 user.aaa.email 就匹配不了</li>
     * <li>通配符 # ，代表一个或多个占位符，或者说一个或多个单词，比如路由为 user.#，那么 user.email 可以匹配，user.aaa.email 也可以匹配</li>
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(RabbitConstants.TOPIC_MODE_QUEUE);
    }

    /**
     * 主题模式绑定队列2
     *
     * @param topicQueue    主题队列
     * @param topicExchange 主题模式交换器
     */
    @Bean
    public Binding topicBinding(Queue topicQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueue).to(topicExchange).with(RabbitConstants.TOPIC_ROUTING_KEY);
    }

}
