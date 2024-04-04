package org.laurichapp.servicecommande.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.laurichapp.servicecommande.dtos.out.CategorieOutDTO;
import org.laurichapp.servicecommande.dtos.out.CommandeDTO;
import org.laurichapp.servicecommande.dtos.out.CouleurDTO;
import org.laurichapp.servicecommande.dtos.out.ProduitCommandeResponseDTO;
import org.laurichapp.servicecommande.dtos.pagination.Paginate;
import org.laurichapp.servicecommande.dtos.pagination.Pagination;
import org.laurichapp.servicecommande.enums.EtatsLivraison;
import org.laurichapp.servicecommande.enums.StatutsPaiment;
import org.laurichapp.servicecommande.exceptions.CommandeNotFoundException;
import org.laurichapp.servicecommande.facades.FacadeCommandeImpl;
import org.laurichapp.servicecommande.models.Commande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class TestGestionnaireController extends TestConfigurationControlleurRest {
    @MockBean
    private FacadeCommandeImpl facadeCommande;


    // ===============================================================================================
    //                                   GET getAllCommandes
    // ===============================================================================================

    /**
     * Si non connecté, Unauthorized
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetAllCommandesUnauthorized(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE

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
     * Si non admin, Forbidden
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetAllCommandesNotAdminUnauthorized(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {

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
     * Si admin OK
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetAllCommandesAdmin(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
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

    @Test
    void testGetCommandeByIdUnauthorized(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get("/gestionnaires/commandes/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

    @Test
    void testGetCommandeByIdNotAdminUnauthorized(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {

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

    @Test
    void testGetCommandeByIdNotFoundAdmin(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        // Définie l'admin en admin
        this.defineAdminUser();
        doThrow(new CommandeNotFoundException()).when(facadeCommande).getCommandeById("123");
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

    @Test
    void testGetCommandeByIdAdmin(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        ProduitCommandeResponseDTO produitCommandeResponseDTO = new ProduitCommandeResponseDTO(10.00, "Homme", "M", "Pantalon", "Ceci est la description de mon pantalon", new CouleurDTO("Bleu"), 2, new CategorieOutDTO("Vêtements"));
        CommandeDTO commandeDTO = new CommandeDTO("123", new Date(), "1", "1", 20.00, List.of(produitCommandeResponseDTO), EtatsLivraison.EN_ATTENTE.name(), StatutsPaiment.ACCEPTE.name(), "12345");
        // Définie l'admin en admin
        this.defineAdminUser();
//        doReturn(Commande.toDTO(commandeDTO)).when(facadeCommande).getCommandeById("123");

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


}
