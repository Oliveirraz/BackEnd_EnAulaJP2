package com.example.enaula.dto;

import com.example.enaula.entity.DiaSemana;
import com.example.enaula.entity.TipoDisponibilidade;

import java.time.LocalDate;
import java.time.LocalTime;

public record DisponibilidadeResponseDTO(
        Long id,
        Long professorId,
        TipoDisponibilidade tipo,
        DiaSemana diaSemana,
        LocalDate data,
        LocalTime horarioInicio,
        LocalTime horarioFim
) {
}