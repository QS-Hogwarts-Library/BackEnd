package com.example.educationalqualityproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.educationalqualityproject.entity.Wizard;
import com.example.educationalqualityproject.repository.WizardRepository;

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

    public boolean emailAlreadyExists(String email) {

        return wizardRepository.existsByEmail(email);
    }

    public Optional<Wizard> findByEmail(String email) {

        return wizardRepository.findByEmail(email);
    }

 public Wizard saveWizard(Wizard wizard) {

    boolean emailExists =
            wizardRepository.existsByEmail(
                    wizard.getEmail()
            );

    if (emailExists && wizard.getId() == null) {

        throw new RuntimeException(
                "Email já cadastrado"
        );
    }

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