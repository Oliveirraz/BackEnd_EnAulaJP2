package com.example.enaula.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record HistoricoAulaResponseDTO(
        Long aulaId,
        LocalDate data,
        LocalTime horario,
        String materia,
        String monitor,
        String status,
        boolean gravacaoDisponivel,
        String urlGravacao
) {
}