package com.example.enaula.dto;

import com.example.enaula.entity.FormatoAula;
import com.example.enaula.entity.Modalidade;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
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
        @Min(
                value = 1,
                message = "A duração mínima da aula é de 1 hora"
        )
        Integer duracao,

        @NotNull(message = "Modalidade é obrigatória")
        Modalidade modalidade,

        @NotNull(message = "O formato da aula é obrigatório")
        FormatoAula formato,

        @NotNull(message = "O valor da aula é obrigatório")
        @DecimalMin(
                value = "0.01",
                message = "O valor da aula deve ser maior que zero"
        )
        BigDecimal valorAula,

        @NotNull(message = "A quantidade de participantes é obrigatória")
        @Min(
                value = 1,
                message = "A quantidade de participantes deve ser no mínimo 1"
        )
        Integer quantidadeParticipantes

) {
}