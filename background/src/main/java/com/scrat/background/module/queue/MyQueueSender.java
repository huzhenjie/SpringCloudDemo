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
    private static final String ROUTING_KEY =  RabbitMQConfig.ROUTING_KEY + "priority";

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
            this.rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, ROUTING_KEY, data);
        } catch (AmqpException e) {
            log.error("Push RabbitMQ message fail", e);
        }
    }

    public void sendPriorityMsg(QueueData<String> data, int priority) {
        final int finalPriority;
        if (priority < 0) {
            log.warn("priority should between 0 and {}", RabbitMQConfig.MAX_PRIORITY);
            finalPriority = 0;
        } else if (priority > RabbitMQConfig.MAX_PRIORITY) {
            log.warn("priority should between 0 and {}", RabbitMQConfig.MAX_PRIORITY);
            finalPriority = RabbitMQConfig.MAX_PRIORITY;
        } else {
            finalPriority = priority;
        }
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, ROUTING_KEY, data, message -> {
            message.getMessageProperties().setPriority(finalPriority);
            return message;
        });
    }
}
