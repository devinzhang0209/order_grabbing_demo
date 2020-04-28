import com.devin.order.OrderGrabbingApplication;
import com.devin.order.config.RabbitConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Devin Zhang
 * @className MqSendTest
 * @description TODO
 * @date 2020/4/28 16:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OrderGrabbingApplication.class})// 指定启动类
@Slf4j
public class MqSendTest {

    @Resource
    private RabbitTemplate rabbitTemplate;


    @Test
    public void mqSend(){
        String message = "topic message";
        rabbitTemplate.convertAndSend(RabbitConstants.TOPIC_MODE_QUEUE, "topic.queue", message);
        log.info("消息发送成功：[{}]", message);
    }
}
