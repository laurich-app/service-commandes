package org.laurichapp.servicecommande.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class TestCommandesController extends TestConfigurationControlleurRest {

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
                get("/commandes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
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
        Paginate<CommandeDTO> p = new Paginate<>(List.of(), new Pagination(2, 10, 0));

        Set<ConstraintViolation<PaginateRequestDTO>> mocked = mock(Set.class);
        doReturn(mocked).when(this.validator).validate(any(PaginateRequestDTO.class));
        doReturn(false).when(mocked).isEmpty();

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get("/commandes")
                        .header("Authorization", "Bearer "+getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    /**
     * Si admin : OK
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetAllCommandesOK(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        Paginate<CommandeDTO> p = new Paginate<>(List.of(), new Pagination(2, 10, 0));
        doReturn(p).when(facadeCommande).getAllCommandes(any());

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get("/commandes")
                        .header("Authorization", "Bearer "+getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    // ===============================================================================================
    //                                   GET getCommande
    // ===============================================================================================

    /**
     * Si non connecté : UNAUTHORIZED
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetCommandeUnauthorized(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get("/commandes/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

    /**
     * Si commande non trouvée : NOT_FOUND
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetCommandeNotFound(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        doThrow(CommandeNotFoundException.class).when(facadeCommande).getCommandeDTOUtilisateurById("123","1");

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get("/commandes/123")
                        .header("Authorization", "Bearer "+getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    /**
     * Si commande trouvée : OK
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetCommandeOK(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        ProduitCommandeResponseDTO produitCommandeResponseDTO = new ProduitCommandeResponseDTO(10.00, "Homme", "M", "Pantalon", "Ceci est la description de mon pantalon", new CouleurDTO("Bleu"), 2, new CategorieOutDTO("Vêtements"));
        CommandeDTO commandeDTO = new CommandeDTO("123", new Date(), "1", "1", 20.00, List.of(produitCommandeResponseDTO), EtatsLivraison.EN_ATTENTE.name(), StatutsPaiment.ACCEPTE.name(), "12345");

        doReturn(commandeDTO).when(facadeCommande).getCommandeDTOUtilisateurById("123","1");

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get("/commandes/123")
                        .header("Authorization", "Bearer "+getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


}
