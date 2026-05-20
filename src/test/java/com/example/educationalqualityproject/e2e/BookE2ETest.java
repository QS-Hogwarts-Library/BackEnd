package com.example.educationalqualityproject.e2e;

import com.example.educationalqualityproject.entity.Wizard;
import com.example.educationalqualityproject.integration.BaseIntegrationTest;
import com.example.educationalqualityproject.repository.BookRepository;
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
public class BookE2ETest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void fluxoCompletoWeb_DeveCadastrarEExibirLivro() throws Exception {
        Wizard loggedWizard = new Wizard();
        loggedWizard.setId("wizard999");

        // 1. Envia formulário HTML via POST para cadastrar (Rota: @PostMapping em /books)
        mockMvc.perform(post("/books")
                        .sessionAttr("loggedWizard", loggedWizard)
                        .param("title", "Codigo Limpo")
                        .param("author", "Robert C. Martin")
                        .param("subject", "Engenharia")
                        .param("statusLeitura", "QUERO_LER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        assertFalse(bookRepository.findAll().isEmpty());

        // 2. Acessa a listagem e valida se renderizou no template Thymeleaf correto ("book/list")
        mockMvc.perform(get("/books")
                        .sessionAttr("loggedWizard", loggedWizard))
                .andExpect(status().isOk())
                .andExpect(view().name("book/list")) // AJUSTADO: "book/list" conforme seu controller real
                .andExpect(model().attributeExists("books"))
                .andExpect(content().string(containsString("Codigo Limpo")));
    }
}