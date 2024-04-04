package org.laurichapp.servicecommande.facades;

import org.laurichapp.servicecommande.dtos.out.CommandeDTO;
import org.laurichapp.servicecommande.dtos.pagination.Paginate;
import org.laurichapp.servicecommande.dtos.pagination.PaginateRequestDTO;
import org.laurichapp.servicecommande.dtos.pagination.Pagination;
import org.laurichapp.servicecommande.dtos.rabbits.GenererCommandeDTO;
import org.laurichapp.servicecommande.dtos.rabbits.NotificationCommandeDTO;
import org.laurichapp.servicecommande.enums.EtatsLivraison;
import org.laurichapp.servicecommande.enums.StatutsPaiment;
import org.laurichapp.servicecommande.exceptions.CommandeNotFoundException;
import org.laurichapp.servicecommande.models.Commande;
import org.laurichapp.servicecommande.models.Panier;
import org.laurichapp.servicecommande.models.commandes.Produit;
import org.laurichapp.servicecommande.repositories.CommandeRepository;
import org.laurichapp.servicecommande.services.ServiceRabbitMQSender;
import org.laurichapp.servicecommande.utils.PageableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FacadeCommandeImpl implements FacadeCommande {

    private CommandeRepository commandeRepository;

    private final ServiceRabbitMQSender serviceRabbitMQSender;

    public FacadeCommandeImpl(CommandeRepository commandeRepository, @Autowired ServiceRabbitMQSender serviceRabbitMQSender) {
        this.commandeRepository = commandeRepository;
        this.serviceRabbitMQSender = serviceRabbitMQSender;
    }

    private double calculTotal(List<Produit> produits) {
        Double sommePanier = 0.0;
        for (Produit p : produits) {
            sommePanier+= p.getPrixUnitaire()*p.getQuantite();
        }
        return sommePanier;
    }

    @Override
    public void createCommande(Panier panier, String idUtilisateur, String email) {

        Commande commande = new Commande();
        commande.setIdUtilisateur(idUtilisateur);
        commande.setNumero(UUID.randomUUID().toString());
        commande.setDateCreation(Date.from(Instant.now()));
        commande.setStatutPaiement(StatutsPaiment.EN_ATTENTE);
        commande.setEtatLivraison(EtatsLivraison.EN_ATTENTE);
        commande.setEmail(email);

        // On récupère l'ID pour traitement dans l'ESB
        Commande commandeInserer = commandeRepository.insert(commande);
        // PROPAGE DANS L'ESB
        this.serviceRabbitMQSender.validerCommande(
                Commande.toValiderCommandeDTO(commandeInserer, panier)
        );
    }

    @Override
    public Paginate<CommandeDTO> getAllCommandesUtilisateur(String idUtilisateur, PaginateRequestDTO paginateRequestDTO) {
        Pageable pageable = PageableUtils.convert(paginateRequestDTO);
        Page<Commande> paginated = commandeRepository.findAllByIdUtilisateur(idUtilisateur, pageable);

        List<CommandeDTO> dtos = paginated.stream().map(Commande::toDTO).toList();

        // Créer un objet Paginate contenant les blogs paginés
        return new Paginate<>(dtos, new Pagination(Math.toIntExact(paginated.getTotalElements()),
                paginateRequestDTO.limit(), paginateRequestDTO.page()));
    }

    @Override
    public Commande getCommandeUtilisateurById(String idCommande, String idUtilisateur) throws CommandeNotFoundException {
        Commande commande = commandeRepository.findCommandeByIdCommandeAndIdUtilisateur(idCommande, idUtilisateur);
        if(commande == null)
            throw new CommandeNotFoundException();
        return commande;
    }


    @Override
    public Paginate<CommandeDTO> getAllCommandes(PaginateRequestDTO paginateRequestDTO) {
        Pageable pageable = PageableUtils.convert(paginateRequestDTO);
        Page<Commande> paginated = commandeRepository.findAll(pageable);

        List<CommandeDTO> dtos = paginated.stream().map(Commande::toDTO).toList();

        // Créer un objet Paginate contenant les blogs paginés
        return new Paginate<>(dtos, new Pagination(Math.toIntExact(paginated.getTotalElements()),
                paginateRequestDTO.limit(), paginateRequestDTO.page()));
    }

    @Override
    public Commande getCommandeById(String idCommande) throws CommandeNotFoundException {
        Optional<Commande> c = commandeRepository.findById(idCommande);
        if(c.isEmpty())
            throw new CommandeNotFoundException();
        return c.get();
    }

    @Override
    public Commande updateEtatLivraison(String idCommande, EtatsLivraison etat) throws CommandeNotFoundException {
        Commande commande = getCommandeById(idCommande);
        commande.setEtatLivraison(etat);
        this.commandeRepository.save(commande);
        return commande;
    }

    @Override
    public void validerCommandeReceptionProduits(GenererCommandeDTO genererCommandeDTO) throws CommandeNotFoundException {
        Commande commande = getCommandeById(genererCommandeDTO.id_commande());
        commande.setProduits(
                genererCommandeDTO.produits().stream().map(Produit::fromProduitCatalogueDTO)
                        .toList()
        );
        commande.setTotal(calculTotal(commande.getProduits()));
        commande.setStatutPaiement(StatutsPaiment.ACCEPTE);
        Commande c = commandeRepository.save(commande);
        this.serviceRabbitMQSender.notifierCommande(
                new NotificationCommandeDTO(
                        c.getEmail(),
                        Commande.toDTO(c)
                )
        );
    }
}
