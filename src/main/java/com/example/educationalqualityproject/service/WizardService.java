package com.example.educationalqualityproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.educationalqualityproject.entity.Wizard;
import com.example.educationalqualityproject.repository.WizardRepository;
import com.example.educationalqualityproject.dto.ViaCepResponse;

@Service
public class WizardService {

    @Autowired
    private WizardRepository wizardRepository;

    // Injetamos o novo serviço do ViaCEP aqui
    @Autowired
    private ViaCepService viaCepService;

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
    // 1. Validação de E-mail: Só executa se o e-mail estiver presente
    if (wizard.getEmail() != null && !wizard.getEmail().isBlank()) {
        // Regex simplificado que aceita e-mails padrão
        if (!wizard.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new RuntimeException("Formato de e-mail inválido");
        }
    } else {
        // Se o e-mail for nulo ou vazio, também deve lançar erro para o cadastro
        throw new RuntimeException("Formato de e-mail inválido");
    }

    // 2. Verificação de existência no banco
    boolean emailExists = wizardRepository.existsByEmail(wizard.getEmail());
    // Garante que não barra o próprio usuário na edição (se ele já tiver ID)
    if (emailExists && wizard.getId() == null) {
        throw new RuntimeException("Email já cadastrado");
    }

    // 3. Lógica ViaCEP
    if (wizard.getCep() != null && !wizard.getCep().isBlank()) {
        ViaCepResponse endereco = viaCepService.consultarCep(wizard.getCep());
        wizard.setLogradouro(endereco.logradouro());
        wizard.setBairro(endereco.bairro());
        wizard.setLocalidade(endereco.localidade());
        wizard.setUf(endereco.uf());
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