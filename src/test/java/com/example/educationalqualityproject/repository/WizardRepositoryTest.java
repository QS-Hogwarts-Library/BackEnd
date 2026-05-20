package com.example.educationalqualityproject.repository;

import com.example.educationalqualityproject.entity.Wizard;
import com.example.educationalqualityproject.integration.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class WizardRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private WizardRepository wizardRepository;

    @BeforeEach
    void setUp() {
        wizardRepository.deleteAll();
    }

    @Test
    void shouldSaveWizard() {
        Wizard wizard = new Wizard("Harry", "harry@hogwarts.com", "MAGIC123");
        Wizard savedWizard = wizardRepository.save(wizard);
        assertNotNull(savedWizard.getId());
    }

    @Test
    void shouldFindWizardByEmail() {
        Wizard wizard = new Wizard("Hermione", "hermione@hogwarts.com", "MAGIC456");
        wizardRepository.save(wizard);

        Optional<Wizard> foundWizard = wizardRepository.findByEmail("hermione@hogwarts.com");
        assertTrue(foundWizard.isPresent());
        assertEquals("Hermione", foundWizard.get().getName());
    }
}