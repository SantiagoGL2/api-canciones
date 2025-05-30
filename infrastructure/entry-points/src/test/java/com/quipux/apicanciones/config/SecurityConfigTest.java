package com.quipux.apicanciones.config;

import com.quipux.apicanciones.security.JwtAuthFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @Test
    @DisplayName("Debería permitir acceso a rutas públicas")
    void publicEndpointsAreAccessible() throws Exception {
        mockMvc.perform(get("/h2-console"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/public"))
                .andExpect(status().isOk());
    }

}