package org.laurichapp.servicecommande.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.laurichapp.servicecommande.facades.FacadeCommande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class CommandesControllerTest {

    @MockBean
    private FacadeCommande facadeCommande;


    /**
     * Si non connecté, Unauthorized
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetCommandeUnauthorized(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

}