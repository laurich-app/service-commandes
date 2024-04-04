package org.laurichapp.servicecommande.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQCatalogueGenererCommande {
    @Value("${spring.rabbitmq.queue.catalogue.generer.commande}")
    private String queue;
    @Value("${spring.rabbitmq.exchange.catalogue.generer.commande}")
    private String exchange;
    @Value("${spring.rabbitmq.routingkey.catalogue.generer.commande}")
    private String routingKey;

    @Bean
    Queue queueCommande() {
        return new Queue(queue, true);
    }

    @Bean
    Exchange myExchangeCommande() {
        return ExchangeBuilder.directExchange(exchange).durable(true).build();
    }

    @Bean
    Binding bindingCommande() {
        return BindingBuilder
                .bind(queueCommande())
                .to(myExchangeCommande())
                .with(routingKey)
                .noargs();
    }
}
