package com.example.enaula.dto;

import com.example.enaula.entity.Modalidade;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record AulaBuscaResponseDTO(

        Long aulaId,

        Long professorId,

        String nomeProfessor,

        Long materiaId,

        String materia,

        BigDecimal valorHoraAula,

        BigDecimal avaliacaoMedia,

        Double distanciaKm,

        Modalidade modalidade,

        LocalDate data,

        LocalTime horario,

        Integer duracao

) {
}