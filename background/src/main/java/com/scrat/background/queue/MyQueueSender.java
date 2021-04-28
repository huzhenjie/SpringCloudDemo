package com.scrat.background.queue;

import com.scrat.background.config.RabbitMQConfig;
import com.scrat.background.model.QueueData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyQueueSender {

//    private final AmqpTemplate rabbitTemplate;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MyQueueSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public boolean send(QueueData<String> data) {
        if (data == null) {
            return false;
        }

        this.rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, data);
        return true;
    }
}
