package com.example.educationalqualityproject.e2e;

import com.example.educationalqualityproject.entity.Wizard;
import com.example.educationalqualityproject.integration.BaseIntegrationTest;
import com.example.educationalqualityproject.repository.WizardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.hamcrest.Matchers.containsString;

@AutoConfigureMockMvc
public class WizardE2ETest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WizardRepository wizardRepository;

    @BeforeEach
    void setUp() {
        wizardRepository.deleteAll();
    }

    @Test
    void fluxoCompletoWebWizard_DeveCadastrarListarEDeletar() throws Exception {
        // 1. Simular preenchimento do formulário HTML de cadastro (Rota: POST /wizards)
        mockMvc.perform(post("/wizards")
                        .param("name", "Ron Weasley")
                        .param("email", "ron@hogwarts.com")
                        .param("magicRegistration", "ROUBADO123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wizards")); // Redireciona para a listagem

        // Valida se persistiu no banco real do contêiner
        assertFalse(wizardRepository.findAll().isEmpty());
        Wizard saved = wizardRepository.findByEmail("ron@hogwarts.com").orElseThrow();

        // 2. Acessa a listagem geral e verifica a renderização do nome no HTML
        mockMvc.perform(get("/wizards"))
                .andExpect(status().isOk())
                .andExpect(view().name("wizard/list")) // View real do seu controller
                .andExpect(model().attributeExists("wizards"))
                .andExpect(content().string(containsString("Ron Weasley")));

        // 3. Acessa a página de detalhes do Bruxo específico
        mockMvc.perform(get("/wizards/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("wizard/details"))
                .andExpect(model().attributeExists("wizard"));
    }
}