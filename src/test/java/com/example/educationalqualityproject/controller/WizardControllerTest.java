package com.example.educationalqualityproject.controller;

import com.example.educationalqualityproject.entity.Wizard;
import com.example.educationalqualityproject.integration.BaseIntegrationTest;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class WizardControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

  @Test
    void shouldAccessWizardsRoute() throws Exception {
        Wizard adminWizard = new Wizard();
        adminWizard.setAdmin(true);
        
        mockMvc.perform(get("/wizards")
                        .sessionAttr("loggedWizard", adminWizard))
                .andExpect(status().isOk());
    }
    @Test
    void shouldAccessLoginPage() throws Exception {

        String response = mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(response != null);
    }
}