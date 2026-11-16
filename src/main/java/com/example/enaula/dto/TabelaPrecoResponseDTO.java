package com.example.enaula.dto;

import com.example.enaula.entity.TitulacaoProfessor;

import java.math.BigDecimal;

public record TabelaPrecoResponseDTO(
        TitulacaoProfessor titulacao,
        BigDecimal valorMinimo
) {
}