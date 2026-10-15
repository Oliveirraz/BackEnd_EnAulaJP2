package com.example.enaula.dto;

import com.example.enaula.entity.TitulacaoProfessor;

import java.math.BigDecimal;

public record ProfessorResponseDTO(

        Long id,
        String nome,
        String email,
        String perfil,
        BigDecimal valorHoraAula,
        TitulacaoProfessor titulacao,
        String foto

) {
}