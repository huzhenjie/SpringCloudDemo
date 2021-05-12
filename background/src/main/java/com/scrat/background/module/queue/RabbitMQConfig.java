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

import java.util.HashMap;
import java.util.Map;

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

    /**
     * Create an AMQP queue
     */
    @Bean
    public Queue queue() {
        boolean durable = true; // true if we are declaring a durable queue (the queue will survive a server restart)
        boolean exclusive = false; // true if we are declaring an exclusive queue (the queue will only be used by the declarer's connection
        boolean autoDelete = false; // true if the server should delete the queue when it is no longer in use
        Map<String, Object> args = new HashMap<>();
        args.put("x-max-priority", 100);
        return new Queue(QUEUE_NAME, durable, exclusive, autoDelete, args);
    }

    /**
     * Create a topic exchange
     */
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    /**
     * Bind the queue and the exchange together
     */
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }
}
