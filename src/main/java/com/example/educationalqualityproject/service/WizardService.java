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

    public Wizard authenticate(
            String email,
            String magicRegistration
    ) {

        Optional<Wizard> wizard =
                wizardRepository.findByEmailAndMagicRegistration(
                        email,
                        magicRegistration
                );

        return wizard.orElse(null);
    }
}