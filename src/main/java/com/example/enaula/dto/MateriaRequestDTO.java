package com.example.enaula.dto;

import jakarta.validation.constraints.NotBlank;

public record MateriaRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Descrição é obrigatória")
        String descricao
) {
}
