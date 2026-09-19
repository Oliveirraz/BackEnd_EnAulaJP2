package com.example.enaula.dto;

import com.example.enaula.entity.Modalidade;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record AulaRequestDTO(

        @NotNull(message = "Matéria é obrigatória")
        Long materiaId,

        @NotNull(message = "Data é obrigatória")
        LocalDate data,

        @NotNull(message = "Horário é obrigatório")
        LocalTime horario,

        @NotNull(message = "Duração é obrigatória")
        @Min(value = 1, message = "A duração mínima da aula é de 1 hora")
        Integer duracao,

        @NotNull(message = "Modalidade é obrigatória")
        Modalidade modalidade
) {
}
