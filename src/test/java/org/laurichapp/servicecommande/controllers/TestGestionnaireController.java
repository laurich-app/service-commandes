package org.laurichapp.servicecommande.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.laurichapp.servicecommande.dtos.in.LivraisonCommandeDTO;
import org.laurichapp.servicecommande.dtos.out.CategorieOutDTO;
import org.laurichapp.servicecommande.dtos.out.CommandeDTO;
import org.laurichapp.servicecommande.dtos.out.CouleurDTO;
import org.laurichapp.servicecommande.dtos.out.ProduitCommandeResponseDTO;
import org.laurichapp.servicecommande.dtos.pagination.Paginate;
import org.laurichapp.servicecommande.dtos.pagination.PaginateRequestDTO;
import org.laurichapp.servicecommande.dtos.pagination.Pagination;
import org.laurichapp.servicecommande.enums.EtatsLivraison;
import org.laurichapp.servicecommande.enums.StatutsPaiment;
import org.laurichapp.servicecommande.exceptions.CommandeNotFoundException;
import org.laurichapp.servicecommande.facades.FacadeCommandeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

class TestGestionnaireController extends TestConfigurationControlleurRest {

    @MockBean
    private FacadeCommandeImpl facadeCommande;
    @MockBean
    private Validator validator;

    // ===============================================================================================
    //                                   GET getAllCommandes
    // ===============================================================================================

    /**
     * Si non connecté : UNAUTHORIZED
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetAllCommandesUnauthorized(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get("/gestionnaires/commandes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

    /**
     * Si non admin : FORBIDDEN
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetAllCommandesNotAdminForbidden(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get("/gestionnaires/commandes")
                        .header("Authorization", "Bearer "+getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    /**
     * Si violation : BAD_REQUEST
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetAllCommandesViolation(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        // Définie l'admin en admin
        this.defineAdminUser();
        Paginate<CommandeDTO> p = new Paginate<>(List.of(), new Pagination(2, 10, 0));
        Set<ConstraintViolation<PaginateRequestDTO>> mocked = mock(Set.class);
        doReturn(mocked).when(this.validator).validate(any(PaginateRequestDTO.class));
        doReturn(false).when(mocked).isEmpty();

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get("/gestionnaires/commandes")
                        .header("Authorization", "Bearer "+getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    /**
     * Si admin et toutes les commandes trouvées : OK
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetAllCommandesOK(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        // Définie l'admin en admin
        this.defineAdminUser();
        Paginate<CommandeDTO> p = new Paginate<>(List.of(), new Pagination(0, 10, 0));
        doReturn(p).when(facadeCommande).getAllCommandes(any());

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get("/gestionnaires/commandes")
                        .header("Authorization", "Bearer "+getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    // ===============================================================================================
    //                                   GET getCommandeById
    // ===============================================================================================

    /**
     * Si non connecté : UNAUTHORIZED
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetCommandeByIdUnauthorized(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get("/gestionnaires/commandes/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

    /**
     * Si non admin : FORBIDDEN
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetCommandeByIdNotAdminForbidden(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get("/gestionnaires/commandes/123")
                        .header("Authorization", "Bearer "+getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    /**
     * Si commande non trouvée : NOT_FOUND
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetCommandeByIdNotFoundAdmin(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        // Définie l'admin en admin
        this.defineAdminUser();
        doThrow(CommandeNotFoundException.class).when(facadeCommande).getCommandeDTOById("123");

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get("/gestionnaires/commandes/123")
                        .header("Authorization", "Bearer "+getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    /**
     * Si admin et commande trouvée : OK
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetCommandeByIdOK(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        // Définie l'admin en admin
        this.defineAdminUser();
        ProduitCommandeResponseDTO produitCommandeResponseDTO = new ProduitCommandeResponseDTO(10.00, "Homme", "M", "Pantalon", "Ceci est la description de mon pantalon", new CouleurDTO("Bleu"), 2, new CategorieOutDTO("Vêtements"));
        CommandeDTO commandeDTO = new CommandeDTO("123", new Date(), "1", "1", 20.00, List.of(produitCommandeResponseDTO), EtatsLivraison.EN_ATTENTE.name(), StatutsPaiment.ACCEPTE.name(), "12345");
        doReturn(commandeDTO).when(facadeCommande).getCommandeDTOById("123");

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get("/gestionnaires/commandes/123")
                        .header("Authorization", "Bearer "+getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    // ===============================================================================================
    //                                   PUT updateCommande
    // ===============================================================================================

    /**
     * Si non connecté : UNAUTHORIZED
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testUpdateCommandeUnauthorized(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        LivraisonCommandeDTO livraisonCommandeDTO = new LivraisonCommandeDTO(EtatsLivraison.EN_ATTENTE);

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                put("/gestionnaires/commandes/123/livraison")
                        .content(objectMapper.writeValueAsString(livraisonCommandeDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //WHEN
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

    /**
     * Si non admin : FORBIDDEN
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testUpdateCommandeNotAdminForbidden(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        LivraisonCommandeDTO livraisonCommandeDTO = new LivraisonCommandeDTO(EtatsLivraison.EN_ATTENTE);

        //WHERE
        MockHttpServletResponse response = mvc.perform(
                put("/gestionnaires/commandes/123/livraison")
                        .header("Authorization", "Bearer "+getAccessToken())
                        .content(objectMapper.writeValueAsString(livraisonCommandeDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //WHEN
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    /**
     * Si commande non trouvée : NOT_FOUND
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testUpdateCommandeNotFoundAdmin(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        // Définie l'admin en admin
        this.defineAdminUser();
        LivraisonCommandeDTO livraisonCommandeDTO = new LivraisonCommandeDTO(EtatsLivraison.EN_ATTENTE);
        doThrow(CommandeNotFoundException.class).when(facadeCommande).updateEtatLivraison(eq("123"), any(EtatsLivraison.class));

        //WHERE
        MockHttpServletResponse response = mvc.perform(
                put("/gestionnaires/commandes/123/livraison")
                        .header("Authorization", "Bearer "+getAccessToken())
                        .content(objectMapper.writeValueAsString(livraisonCommandeDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //WHEN
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    /**
     * Si admin et commande mise a jour : CREATED
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testUpdateCommandeOK(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        // Définie l'admin en admin
        this.defineAdminUser();
        ProduitCommandeResponseDTO produitCommandeResponseDTO = new ProduitCommandeResponseDTO(10.00, "Homme", "M", "Pantalon", "Ceci est la description de mon pantalon", new CouleurDTO("Bleu"), 2, new CategorieOutDTO("Vêtements"));
        CommandeDTO commandeDTO = new CommandeDTO("123", new Date(), "1", "1", 20.00, List.of(produitCommandeResponseDTO), EtatsLivraison.EN_ATTENTE.name(), StatutsPaiment.ACCEPTE.name(), "12345");
        LivraisonCommandeDTO livraisonCommandeDTO = new LivraisonCommandeDTO(EtatsLivraison.EN_COURS);
        doReturn(commandeDTO).when(facadeCommande).updateEtatLivraison(eq("123"), any(EtatsLivraison.class));

        //WHERE
        MockHttpServletResponse response = mvc.perform(
                put("/gestionnaires/commandes/123/livraison")
                        .header("Authorization", "Bearer "+getAccessToken())
                        .content(objectMapper.writeValueAsString(livraisonCommandeDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //WHEN
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

}
