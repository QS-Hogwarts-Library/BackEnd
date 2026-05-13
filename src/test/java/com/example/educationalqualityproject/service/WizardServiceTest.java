package com.example.educationalqualityproject.service;

import com.example.educationalqualityproject.entity.Wizard;
import com.example.educationalqualityproject.repository.WizardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WizardServiceTest {

    @Autowired
    private WizardRepository wizardRepository;

    public Wizard saveWizard(Wizard wizard) {
        if (wizardRepository.existsByEmail(wizard.getEmail())) {
            throw new RuntimeException("Este e-mail já está cadastrado em Hogwarts!");
        }
        return wizardRepository.save(wizard);
    }

    public boolean existsByEmail(String email) {
        return wizardRepository.existsByEmail(email);
    }

    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
