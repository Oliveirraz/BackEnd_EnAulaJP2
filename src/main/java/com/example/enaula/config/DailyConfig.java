package com.example.enaula.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class DailyConfig {

    // Obtém a chave de API da Daily.co definida no application.properties.
    // Essa chave é utilizada para autenticar as requisições à API.
    @Value("${daily.api-key}")
    private String apiKey;

    // Obtém a URL base da API da Daily.co definida no application.properties.
    // Essa URL indica o endereço para o qual as requisições serão enviadas.
    @Value("${daily.api-url}")
    private String apiUrl;

    // Cria e disponibiliza um RestClient como Bean do Spring.
    // Ele será utilizado para realizar requisições HTTP à API da Daily.co.
    @Bean
    public RestClient dailyRestClient() {
        return RestClient.builder()
                // Define a URL base utilizada nas requisições.
                .baseUrl(apiUrl)
                // Adiciona o cabeçalho de autenticação Bearer,
                // enviando a chave de API para autorizar as requisições.
                .defaultHeader("Authorization", "Bearer " + apiKey)
                // Define o formato do conteúdo enviado nas requisições como JSON.
                .defaultHeader("Content-Type", "application/json")
                // Finaliza a configuração e cria o cliente HTTP.
                .build();
    }
}
