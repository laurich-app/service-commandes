package org.laurichapp.servicecommande.services;

import org.laurichapp.servicecommande.dtos.rabbits.NotificationCommandeDTO;
import org.laurichapp.servicecommande.dtos.rabbits.ValiderCommandeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ServiceRabbitMQSender {
    @Value("${spring.rabbitmq.exchange.commande.valider.commande}")
    private String exchangeCommandeValiderCommande;

    @Value("${spring.rabbitmq.routingkey.commande.valider.commande}")
    private String routingkeyCommandeValiderCommande;

    @Value("${spring.rabbitmq.exchange.catalogue.generer.commande.notification}")
    private String exchangeCommandeNotification;

    @Value("${spring.rabbitmq.routingkey.catalogue.generer.commande.notification}")
    private String routingkeyCommandeNotification;

    private final RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ServiceRabbitMQSender.class);

    @Autowired
    public ServiceRabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void validerCommande(ValiderCommandeDTO validerCommandeDTO){
        logger.info("Valider commande : " + validerCommandeDTO);
        rabbitTemplate.convertAndSend(exchangeCommandeValiderCommande,routingkeyCommandeValiderCommande, validerCommandeDTO);
    }

    public void notifierCommande(NotificationCommandeDTO n) {
        logger.info("Notifier l'utilisateur : " + n);
        rabbitTemplate.convertAndSend(exchangeCommandeNotification,routingkeyCommandeNotification, n);
    }
}
