package com.example.enaula.dto;

public record MateriaResponseDTO(
        Long id,
        String nome,
        String descricao,
        Long professorId
) {
}
