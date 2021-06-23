package com.scrat.background.module.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Component;

@Component
public class MQBinder {
    private static Logger log = LoggerFactory.getLogger(MQBinder.class.getName());
    private final RabbitAdmin rabbitAdmin;

    public MQBinder(RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }

    public boolean bindQueue(String queueName, String exchangeName, String routingKey) {
        try {
            Queue queue = QueueBuilder.durable(queueName).maxPriority(RabbitMQConfig.MAX_PRIORITY).build();
            rabbitAdmin.declareQueue(queue);

            DirectExchange directExchange = ExchangeBuilder.directExchange(exchangeName).delayed().build();
            rabbitAdmin.declareExchange(directExchange);

            Binding binding = BindingBuilder.bind(queue).to(directExchange).with(routingKey);
            rabbitAdmin.declareBinding(binding);

            return true;
        } catch (Exception e) {
            log.error("Bind queue fail", e);
            return false;
        }
    }
}
