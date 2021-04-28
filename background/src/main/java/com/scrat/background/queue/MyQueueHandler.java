package com.scrat.background.queue;

import com.scrat.background.config.RabbitMQConfig;
import com.scrat.background.model.QueueData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MyQueueHandler {
    private static Logger log = LoggerFactory.getLogger(MyQueueHandler.class.getName());

    @RabbitHandler
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleCallback(QueueData<String> data) {
        try {
            System.out.println(data);
        } catch (Exception e) {
            log.error("【回调】回调失败", e);
        }
    }
}
