package com.scrat.background.module.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyQueueSender {
    private static Logger log = LoggerFactory.getLogger(MyQueueSender.class.getName());

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MyQueueSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendSimpleMsg(QueueData<String> data) {
        if (data == null) {
            log.error("Push RabbitMQ message fail");
            return;
        }

        try {
            this.rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, data);
        } catch (AmqpException e) {
            log.error("Push RabbitMQ message fail", e);
        }
    }
}
