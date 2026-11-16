package com.example.enaula.dto;

public record AlunoResponseDTO(Long id, String nome, String email, String foto,
                               // Link temporário para exibir a foto (null se não há foto)
                               String fotoUrl) {
}
