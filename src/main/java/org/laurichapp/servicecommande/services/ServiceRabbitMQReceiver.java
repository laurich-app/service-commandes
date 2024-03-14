package org.laurichapp.servicecommande.services;

import org.laurichapp.servicecommande.dtos.rabbits.GenererCommandeDTO;
import org.laurichapp.servicecommande.dtos.rabbits.SupprimerStockDTO;
import org.laurichapp.servicecommande.exceptions.CommandeNotFoundException;
import org.laurichapp.servicecommande.facades.FacadeCommande;
import org.laurichapp.servicecommande.facades.FacadePanier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceRabbitMQReceiver implements RabbitListenerConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(ServiceRabbitMQReceiver.class);

    private final FacadePanier facadePanier;
    private final FacadeCommande facadeCommande;

    public ServiceRabbitMQReceiver(@Autowired FacadePanier facadePanier, @Autowired FacadeCommande facadeCommande) {
        this.facadePanier = facadePanier;
        this.facadeCommande = facadeCommande;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.catalogue.stock.supprimer}")
    public void consumeStockSupprimer(SupprimerStockDTO stockDTO) {
        logger.info("Stock supprimé : " + stockDTO);
        facadePanier.deleteProduitPanierByIdAndCommande(stockDTO.id_produit(), stockDTO.couleur());
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.catalogue.generer.commande}")
    public void consumeGenererCommande(GenererCommandeDTO genererCommandeDTO) {
        logger.info("Generer commande : " + genererCommandeDTO);
        try {
            facadeCommande.validerCommandeReceptionProduits(genererCommandeDTO);
        } catch (CommandeNotFoundException e) {
            logger.error("Commande introuvable : "+genererCommandeDTO);
        }
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {

    }
}
