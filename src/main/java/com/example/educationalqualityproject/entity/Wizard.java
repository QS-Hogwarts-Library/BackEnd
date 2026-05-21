package com.example.educationalqualityproject.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "wizards")
public class Wizard {

    @Id
    private String id;

    private String name;
    private String email;
    private String magicRegistration;
    
    // Novos campos de endereço (ViaCEP)
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;

    private boolean admin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Wizard() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Wizard(String name, String email, String magicRegistration) {
        this();
        this.name = name;
        this.email = email;
        this.magicRegistration = magicRegistration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.updatedAt = LocalDateTime.now();
    }

    public String getMagicRegistration() {
        return magicRegistration;
    }

    public void setMagicRegistration(String magicRegistration) {
        this.magicRegistration = magicRegistration;
        this.updatedAt = LocalDateTime.now();
    }

    // --- Início dos Getters e Setters de Endereço ---

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
        this.updatedAt = LocalDateTime.now();
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
        this.updatedAt = LocalDateTime.now();
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
        this.updatedAt = LocalDateTime.now();
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
        this.updatedAt = LocalDateTime.now();
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
        this.updatedAt = LocalDateTime.now();
    }
    
    // --- Fim dos Getters e Setters de Endereço ---

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
        this.updatedAt = LocalDateTime.now();
    }
}