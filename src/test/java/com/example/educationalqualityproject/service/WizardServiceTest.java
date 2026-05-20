package com.example.educationalqualityproject.service;

import com.example.educationalqualityproject.entity.Wizard;
import com.example.educationalqualityproject.integration.BaseIntegrationTest;
import com.example.educationalqualityproject.repository.WizardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class WizardServiceTest extends BaseIntegrationTest {

    @Autowired
    private WizardService wizardService;

    @Autowired
    private WizardRepository wizardRepository;

    @BeforeEach
    void setUp() {
        wizardRepository.deleteAll();
    }

    @Test
    void shouldSaveWizard() {
        Wizard wizard = new Wizard("Harry", "harry@test.com", "123");
        Wizard saved = wizardService.saveWizard(wizard);

        assertNotNull(saved.getId());
        assertEquals("Harry", saved.getName());
    }

    @Test
    void shouldFindWizardByEmail() {
        Wizard wizard = new Wizard("Hermione", "hermione@test.com", "123");
        wizardService.saveWizard(wizard);

        Optional<Wizard> found = wizardService.findByEmail("hermione@test.com");
        assertTrue(found.isPresent());
        assertEquals("Hermione", found.get().getName());
    }

    @Test
    void shouldNotAllowDuplicateEmail() {
        Wizard wizard1 = new Wizard("Harry", "duplicado@test.com", "111");
        Wizard wizard2 = new Wizard("Outro Harry", "duplicado@test.com", "222");

        wizardService.saveWizard(wizard1);

        assertThrows(RuntimeException.class, () -> wizardService.saveWizard(wizard2));
    }

    @Test
    void shouldAllowUpdateEvenIfEmailExistsForSameWizard() {
        Wizard wizard = new Wizard("Hermione", "hermione@test.com", "123");
        Wizard saved = wizardService.saveWizard(wizard);

        saved.setName("Hermione Granger");
        Wizard updated = wizardService.saveWizard(saved);

        assertEquals("Hermione Granger", updated.getName());
    }
}