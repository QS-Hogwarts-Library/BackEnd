package com.example.educationalqualityproject.service;

import com.example.educationalqualityproject.dto.ViaCepResponse;
import com.example.educationalqualityproject.util.VcrHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ViaCepVcrTest {

    @Autowired
    private ViaCepService viaCepService;

    private VcrHelper vcrHelper;

    @BeforeEach
    void setUp() throws IOException {
        // Inicializa o VCRHelper apontando para a pasta de cassetes
        // O terceiro parâmetro 'false' indica modo de reprodução (playback)
        vcrHelper = new VcrHelper("src/test/resources/vcr-cassettes", "cep_01001000", false);
        vcrHelper.start();
    }

    @AfterEach
    void tearDown() throws IOException {
        if (vcrHelper != null) {
            vcrHelper.stop();
        }
    }

    @Test
    void deveConsultarCepComSucesso() {
        // A chamada ao viaCepService usará o servidor mock iniciado pelo VcrHelper
        ViaCepResponse resultado = viaCepService.consultarCep("01001000");

        assertNotNull(resultado);
        assertEquals("01001-000", resultado.cep());
        assertEquals("Praça da Sé", resultado.logradouro());
        assertEquals("São Paulo", resultado.localidade());
    }
}