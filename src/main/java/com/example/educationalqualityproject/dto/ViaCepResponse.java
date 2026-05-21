package com.example.educationalqualityproject.dto;

public record ViaCepResponse(
    String cep,
    String logradouro,
    String complemento,
    String bairro,
    String localidade,
    String uf,
    String erro
) {}