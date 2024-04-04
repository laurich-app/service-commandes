package org.laurichapp.servicecommande.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.laurichapp.servicecommande.dtos.out.CommandeDTO;
import org.laurichapp.servicecommande.dtos.pagination.Paginate;
import org.laurichapp.servicecommande.dtos.pagination.Pagination;
import org.laurichapp.servicecommande.facades.FacadeCommandeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class TestGestionnaireController extends TestConfigurationControlleurRest {
    @MockBean
    private FacadeCommandeImpl facadeCommande;

    /**
     * Si non connecté, Unauthorized
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testCreerCategoriesUnauthorized(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
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
    void testCreerCategoriesNotAdminUnauthorized(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {

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
     * Si non admin, Forbidden
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testCreerCategoriesAdmin(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
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
}
