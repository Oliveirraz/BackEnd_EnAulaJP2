package com.example.enaula.dto;

import com.example.enaula.entity.DiaSemana;
import com.example.enaula.entity.TipoDisponibilidade;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record DisponibilidadeRequestDTO(

        @NotNull(message = "O tipo de disponibilidade é obrigatório")
        TipoDisponibilidade tipo,

        DiaSemana diaSemana,

        LocalDate data,

        @NotNull(message = "O horário inicial é obrigatório")
        LocalTime horarioInicio,

        @NotNull(message = "O horário final é obrigatório")
        LocalTime horarioFim

) {
}