package org.laurichapp.servicecommande.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.laurichapp.servicecommande.repositories.CommandeRepository;
import org.laurichapp.servicecommande.repositories.PanierRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

// https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing
@SpringBootTest
@AutoConfigureMockMvc
public abstract class TestConfigurationControlleurRest {

    /**
     * Pour le startup.
     */
    @MockBean
    private JwtDecoder jwtDecoder;

    @MockBean
    private CommandeRepository commandeRepository;

    @MockBean
    private PanierRepository panierRepository;

    private String accessToken;

    private Map<String, Object> claims;

    @BeforeEach
    public void init() {
        accessToken = "accessToken";
        Jwt jwt = mock(Jwt.class);
        doReturn(jwt).when(jwtDecoder).decode(accessToken);
        claims = new HashMap<>();
        doReturn(claims).when(jwt).getClaims();
        claims.put("roles", List.of("USER"));
        doReturn("1").when(jwt).getSubject();
        claims.put("email", "email@email.com");
    }

    public String getAccessToken() {
        return accessToken;
    }

    protected void defineAdminUser() {
        claims.put("roles", List.of("USER", "GESTIONNAIRE"));
    }

    /**
     * Permet de récupérer les base URI, pour les TESTS
     * @return
     */
    protected String getBaseUri(HttpServletRequest request) {
        return ServletUriComponentsBuilder
                .fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
    }
}
