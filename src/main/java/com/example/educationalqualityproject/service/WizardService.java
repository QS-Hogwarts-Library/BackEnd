package com.example.educationalqualityproject.service;

import com.example.educationalqualityproject.entity.Wizard;
import com.example.educationalqualityproject.repository.WizardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WizardService {

    @Autowired
    private WizardRepository wizardRepository;

    public List<Wizard> getAllWizards() {
        return wizardRepository.findAll();
    }

    public Optional<Wizard> getWizardById(String id) {
        return wizardRepository.findById(id);
    }

    public Wizard saveWizard(Wizard wizard) {
        return wizardRepository.save(wizard);
    }

    public void deleteWizard(String id) {
        wizardRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return wizardRepository.existsByEmail(email);
    }

    public boolean existsByMagicRegistration(String magicRegistration) {
        return wizardRepository.existsByMagicRegistration(magicRegistration);
    }

    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}