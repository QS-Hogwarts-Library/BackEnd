package com.example.educationalqualityproject.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.educationalqualityproject.dto.ViaCepResponse;

@Service
public class ViaCepService {

    public ViaCepResponse consultarCep(String cep) {
        // Limpa a string para garantir que só tem números
        String cepLimpo = cep.replaceAll("[^0-9]", "");
        
        if (cepLimpo.length() != 8) {
            throw new IllegalArgumentException("Formato de CEP inválido");
        }


        String url = "https://viacep.com.br/ws/" + cepLimpo + "/json/";
        RestTemplate restTemplate = new RestTemplate();
        
        ViaCepResponse response = restTemplate.getForObject(url, ViaCepResponse.class);
        
        // A API do ViaCEP retorna "erro": "true" quando o formato está certo mas o CEP não existe
        if (response != null && "true".equals(response.erro())) {
            throw new RuntimeException("CEP não encontrado na base do Correios/ViaCEP");
        }
        
        return response;
    }
}