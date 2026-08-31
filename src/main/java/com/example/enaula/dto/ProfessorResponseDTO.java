package com.example.enaula.dto;

import java.math.BigDecimal;

public record ProfessorResponseDTO(

        Long id,
        String nome,
        String email,
        String perfil,
        BigDecimal valorHoraAula

) {
}