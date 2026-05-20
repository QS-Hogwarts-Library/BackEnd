package com.example.educationalqualityproject.controller;

import com.example.educationalqualityproject.entity.Wizard;
import com.example.educationalqualityproject.integration.BaseIntegrationTest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class BookControllerParameterizedTest
        extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvSource({
            "Harry Potter,J.K Rowling,Fantasia,LENDO",
            "Senhor dos Aneis,Tolkien,Aventura,LIDO",
            "1984,George Orwell,Ficcao,QUERO_LER"
    })
    void shouldCreateBooksWithDifferentData(
            String title,
            String author,
            String subject,
            String statusLeitura
    ) throws Exception {

        Wizard wizard = new Wizard();

        wizard.setId("wizard123");

        mockMvc.perform(post("/books")
                        .sessionAttr(
                                "loggedWizard",
                                wizard
                        )
                        .param("title", title)
                        .param("author", author)
                        .param("subject", subject)
                        .param(
                                "statusLeitura",
                                statusLeitura
                        ))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }
}