package org.laurichapp.servicecommande.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQCatalogueStockSupprimer {
    @Value("${spring.rabbitmq.queue.catalogue.stock.supprimer}")
    private String queue;
    @Value("${spring.rabbitmq.exchange.catalogue.stock.supprimer}")
    private String exchange;
    @Value("${spring.rabbitmq.routingkey.catalogue.stock.supprimer}")
    private String routingKey;

    @Bean
    Queue queue() {
        return new Queue(queue, true);
    }

    @Bean
    Exchange myExchange() {
        return ExchangeBuilder.directExchange(exchange).durable(true).build();
    }

    @Bean
    Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(myExchange())
                .with(routingKey)
                .noargs();
    }
}
