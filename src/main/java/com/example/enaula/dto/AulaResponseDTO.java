package com.example.enaula.dto;

import com.example.enaula.entity.Modalidade;

import java.time.LocalDate;
import java.time.LocalTime;

public record AulaResponseDTO(

        Long id,
        Long materiaId,
        String materiaNome,
        Long professorId,
        LocalDate data,
        LocalTime horario,
        Integer duracao,
        Modalidade modalidade
) {
}