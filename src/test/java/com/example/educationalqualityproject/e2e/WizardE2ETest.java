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
        // 1. Cadastra (não precisa de sessão, é público)
        mockMvc.perform(post("/wizards")
                        .param("name", "Ron Weasley")
                        .param("email", "ron@hogwarts.com")
                        .param("magicRegistration", "ROUBADO123")
                        .param("cep", "")) 
                .andExpect(status().is3xxRedirection());

        // Valida se persistiu no banco real do Testcontainers
        assertFalse(wizardRepository.findAll().isEmpty());
        Wizard saved = wizardRepository.findByEmail("ron@hogwarts.com").orElseThrow();

        // 2. Prepara o admin para as próximas chamadas
        Wizard admin = new Wizard();
        admin.setAdmin(true);

        // 3. Acede à listagem geral (autenticado) e verifica o conteúdo
        mockMvc.perform(get("/wizards").sessionAttr("loggedWizard", admin))
                .andExpect(status().isOk())
                .andExpect(view().name("wizard/list"))
                .andExpect(model().attributeExists("wizards"))
                .andExpect(content().string(containsString("Ron Weasley")));

        // 4. Acede à página de detalhes do Bruxo específico
        mockMvc.perform(get("/wizards/" + saved.getId()).sessionAttr("loggedWizard", admin))
                .andExpect(status().isOk())
                .andExpect(view().name("wizard/details"))
                .andExpect(model().attributeExists("wizard"));
    }
}