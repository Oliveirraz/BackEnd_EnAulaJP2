package com.example.enaula.dto;

public record LoginResponseDTO(
        String token,
        String tipo,
        Long id,
        String nome,
        String email
) {
}