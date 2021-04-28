package com.scrat.background.module.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_NAME = "my_queue";
    public static final String EXCHANGE_NAME = "my_exchange";
    public static final String ROUTING_KEY = "all";

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue registerBookQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange registerBookTopicExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding registerBookBinding() {
        return BindingBuilder.bind(registerBookQueue())
                .to(registerBookTopicExchange())
                .with(ROUTING_KEY);
    }
}
