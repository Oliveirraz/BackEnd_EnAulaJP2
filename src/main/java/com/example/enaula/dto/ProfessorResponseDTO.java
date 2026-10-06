package com.example.enaula.dto;

import com.example.enaula.entity.TitulacaoMonitor;

import java.math.BigDecimal;

public record ProfessorResponseDTO(

        Long id,
        String nome,
        String email,
        String perfil,
        BigDecimal valorHoraAula,
        TitulacaoMonitor titulacao,
        String foto

) {
}