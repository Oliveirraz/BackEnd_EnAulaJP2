package com.example.enaula.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RecusaMatriculaRequestDTO(

        @NotBlank(message = "Informe o motivo da recusa")
        @Size(
                max = 500,
                message = "O motivo deve ter no máximo 500 caracteres"
        )
        String motivo

) {}
