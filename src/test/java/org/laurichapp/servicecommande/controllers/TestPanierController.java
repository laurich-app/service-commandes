package org.laurichapp.servicecommande.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.laurichapp.servicecommande.dtos.in.AjouterProduitDTO;
import org.laurichapp.servicecommande.dtos.in.UpdateProduitDTO;
import org.laurichapp.servicecommande.dtos.out.CouleurDTO;
import org.laurichapp.servicecommande.dtos.out.PanierDTO;
import org.laurichapp.servicecommande.dtos.out.ProduitPanierResponseDTO;
import org.laurichapp.servicecommande.exceptions.PanierNotFoundException;
import org.laurichapp.servicecommande.exceptions.ProduitPasDansPanierException;
import org.laurichapp.servicecommande.facades.FacadePanierImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

class TestPanierController extends TestConfigurationControlleurRest {

    @MockBean
    private FacadePanierImpl facadePanier;

    // ===============================================================================================
    //                                   GET getPanier
    // ===============================================================================================

    /**
     * Si panier non trouvé : NOT_FOUND
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetPanierNotFound(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        doThrow(PanierNotFoundException.class).when(facadePanier).getPanier("123456");

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get("/paniers/123456")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    /**
     * Si panier trouvé : OK
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testGetPanierOK(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        ProduitPanierResponseDTO produitPanierResponseDTO = new ProduitPanierResponseDTO(1, new CouleurDTO("Rouge"), 3);
        PanierDTO panierDTO = new PanierDTO("1", new Date(), "123456", List.of(produitPanierResponseDTO));
        doReturn(panierDTO).when(facadePanier).getPanier("123456");

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                get("/paniers/123456")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    // ===============================================================================================
    //                                   POST createPanier
    // ===============================================================================================

    /**
     * Si panier créé : CREATED
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testCreatePanierOK(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        ProduitPanierResponseDTO produitPanierResponseDTO = new ProduitPanierResponseDTO(1, new CouleurDTO("Rouge"), 3);
        PanierDTO panierDTO = new PanierDTO("1", new Date(), "123456", List.of(produitPanierResponseDTO));
        AjouterProduitDTO produitDTO = new AjouterProduitDTO(1, "Rouge", 3);
        doReturn(panierDTO).when(facadePanier).createPanier(produitDTO);

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                post("/paniers")
                        .content(objectMapper.writeValueAsString(produitDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    // ===============================================================================================
    //                                   POST createCommandeFromPanier
    // ===============================================================================================

//    /**
//     * Si non connecté : UNAUTHORIZED
//     * @param mvc
//     * @param objectMapper
//     * @throws Exception
//     */
//    @Test
//    void testCreateCommandeFromPanierUnauthorized(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
//        // WHERE
//        MockHttpServletResponse response = mvc.perform(
//                post("/paniers/123456/valider_commande")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//        ).andReturn().getResponse();
//
//        // WHEN
//        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
//    }
//
//    /**
//     * Si panier non trouvé : NOT_FOUND
//     * @param mvc
//     * @param objectMapper
//     * @throws Exception
//     */
//    @Test
//    void testCreateCommandeFromPanierNotFound(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
//        // BEFORE
//        doThrow(PanierNotFoundException.class).when(facadePanier).createCommandeFromPanier("123456","1","test@test.com");
//
//        // WHERE
//        MockHttpServletResponse response = mvc.perform(
//                post("/paniers/123456/valider_commande")
//                        .header("Authorization", "Bearer "+getAccessToken())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//        ).andReturn().getResponse();
//
//        // WHEN
//        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
//    }

    // ===============================================================================================
    //                                   POST addProduit
    // ===============================================================================================

    /**
     * Si panier n'existe pas : NOT_FOUND
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testAddProduitNotFound(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        AjouterProduitDTO produitDTO = new AjouterProduitDTO(1, "Rouge", 3);
        doThrow(PanierNotFoundException.class).when(facadePanier).addProduit("123456", produitDTO);

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                post("/paniers/123456")
                        .content(objectMapper.writeValueAsString(produitDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    /**
     * Si panier existe et produit ajouté : CREATED
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testAddProduitOK(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        ProduitPanierResponseDTO produitPanierResponseDTO = new ProduitPanierResponseDTO(1, new CouleurDTO("Rouge"), 3);
        PanierDTO panierDTO = new PanierDTO("1", new Date(), "123456", List.of(produitPanierResponseDTO));
        AjouterProduitDTO produitDTO = new AjouterProduitDTO(1, "Rouge", 3);
        doReturn(panierDTO).when(facadePanier).addProduit("123456", produitDTO);

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                post("/paniers/123456")
                        .content(objectMapper.writeValueAsString(produitDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    // ===============================================================================================
    //                                   PUT updateProduit
    // ===============================================================================================

    /**
     * Si panier n'est pas trouvé : NOT_FOUND
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testUpdateProduitPanierNotFoundException(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        UpdateProduitDTO produitDTO = new UpdateProduitDTO("Bleu", 4);
        doThrow(PanierNotFoundException.class).when(facadePanier).updateProduit("123456",1, produitDTO);

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                put("/paniers/123456/produits/1")
                        .content(objectMapper.writeValueAsString(produitDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    /**
     * Si produit n'est pas trouvé : NOT_FOUND
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testUpdateProduitProduitPasDansPanierException(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        UpdateProduitDTO produitDTO = new UpdateProduitDTO("Bleu", 4);
        doThrow(ProduitPasDansPanierException.class).when(facadePanier).updateProduit("123456",2, produitDTO);

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                put("/paniers/123456/produits/2")
                        .content(objectMapper.writeValueAsString(produitDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    /**
     * Si panier mis a jour : OK
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testUpdateProduitOK(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        ProduitPanierResponseDTO produitPanierResponseDTO = new ProduitPanierResponseDTO(1, new CouleurDTO("Rouge"), 3);
        PanierDTO panierDTO = new PanierDTO("1", new Date(), "123456", List.of(produitPanierResponseDTO));
        UpdateProduitDTO produitDTO = new UpdateProduitDTO("Bleu", 4);
        doReturn(panierDTO).when(facadePanier).updateProduit("123456",1, produitDTO);

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                put("/paniers/123456/produits/1")
                        .content(objectMapper.writeValueAsString(produitDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    // ===============================================================================================
    //                                   DELETE deletePanier
    // ===============================================================================================

    /**
     * Si panier n'est pas trouvé : NOT_FOUND
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testDeletePanierNotFound(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        doThrow(PanierNotFoundException.class).when(facadePanier).deletePanier("123456");

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                delete("/paniers/123456")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    /**
     * Si panier supprimé : NO_CONTENT
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testDeletePanierOK(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        doNothing().when(facadePanier).deletePanier("123456");

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                delete("/paniers/123456")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    // ===============================================================================================
    //                                   DELETE deleteProduitPanier
    // ===============================================================================================

    /**
     * Si produit n'est pas trouvé : NOT_FOUND
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testDeleteProduitPanierProduitPasDansPanierException(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        doThrow(ProduitPasDansPanierException.class).when(facadePanier).deleteProduitPanier("123456", 1, "Bleu");

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                delete("/paniers/123456/produits/1/couleurs/Bleu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    /**
     * Si panier n'est pas trouvé : NOT_FOUND
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testDeleteProduitPanierPanierNotFoundException(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        doThrow(PanierNotFoundException.class).when(facadePanier).deleteProduitPanier("123456", 1, "Bleu");

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                delete("/paniers/123456/produits/1/couleurs/Bleu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    /**
     * Si produit supprimé du panier : NO_CONTENT
     * @param mvc
     * @param objectMapper
     * @throws Exception
     */
    @Test
    void testDeleteProduitPanierOK(@Autowired MockMvc mvc, @Autowired ObjectMapper objectMapper) throws Exception {
        // BEFORE
        doNothing().when(facadePanier).deleteProduitPanier("123456", 1, "Bleu");

        // WHERE
        MockHttpServletResponse response = mvc.perform(
                delete("/paniers/123456/produits/1/couleurs/Bleu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // WHEN
        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }








}
