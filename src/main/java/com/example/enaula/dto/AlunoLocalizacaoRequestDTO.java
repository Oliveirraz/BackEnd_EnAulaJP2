package com.example.enaula.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record AlunoLocalizacaoRequestDTO(

        @NotNull(message = "A latitude é obrigatória")
        @DecimalMin(value = "-90.0")
        @DecimalMax(value = "90.0")
        Double latitude,

        @NotNull(message = "A longitude é obrigatória")
        @DecimalMin(value = "-180.0")
        @DecimalMax(value = "180.0")
        Double longitude

) {
}