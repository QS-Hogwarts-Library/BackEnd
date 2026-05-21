package com.example.educationalqualityproject.service;

import com.example.educationalqualityproject.entity.Wizard;
import com.example.educationalqualityproject.repository.WizardRepository; 
import com.example.educationalqualityproject.integration.BaseIntegrationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class WizardServiceParameterizedTest extends BaseIntegrationTest {

    @Autowired
    private WizardService wizardService;

    @Autowired
    private WizardRepository wizardRepository;

    @BeforeEach
    void setUp() {
        wizardRepository.deleteAll();
    }

    @ParameterizedTest
    @CsvSource({
            "Harry Potter,harry@hogwarts.com,EXPECTO_PATRONUM",
            "Hermione Granger,hermione@hogwarts.com,WINGARDIUM_LEVIOSA",
            "Ron Weasley,ron@hogwarts.com,CHOCO_FROG",
            "Draco Malfoi, draquinho@sonserina.com, BOOKS_OF_THE_BOOKS" 
    })
    @DisplayName("Deve criar múltiplos wizards")
    void shouldCreateDifferentWizards(String name, String email, String magicRegistration) {
        Wizard wizard = new Wizard(name, email, magicRegistration);
        Wizard savedWizard = wizardService.saveWizard(wizard);

        assertNotNull(savedWizard.getId());
        assertEquals(name, savedWizard.getName());
        assertEquals(email, savedWizard.getEmail());
        assertEquals(magicRegistration, savedWizard.getMagicRegistration());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidEmails")
    @DisplayName("Deve validar emails inválidos")
    void shouldValidateInvalidEmails(String invalidEmail) {
        Wizard wizard = new Wizard("Harry", invalidEmail, "MAGIC123");

        // Se o seu service lançar outra exceção específica de validação, altere aqui
        assertThrows(RuntimeException.class, () -> wizardService.saveWizard(wizard));
    }

    static Stream<Arguments> provideInvalidEmails() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("emailinvalido"),
                Arguments.of("@hogwarts.com"),
                Arguments.of("harry@")
        );
    }

    @ParameterizedTest
    @CsvSource({
            "Harry Potter,harry@test.com",
            "Hermione,hermione@test.com",
            "Ron,ron@test.com"
    })
    @DisplayName("Deve buscar wizard por email")
    void shouldFindWizardByEmail(String name, String email) {
        Wizard wizard = new Wizard(name, email, "MAGIC123");
        wizardService.saveWizard(wizard);

        Optional<Wizard> foundWizard = wizardService.findByEmail(email);

        assertTrue(foundWizard.isPresent());
        assertThat(foundWizard.get().getName()).isEqualTo(name);
        assertThat(foundWizard.get().getEmail()).isEqualTo(email);
    }
}