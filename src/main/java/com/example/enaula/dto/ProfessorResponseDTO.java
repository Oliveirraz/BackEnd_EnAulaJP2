package com.example.enaula.ResponseDTO;

import java.math.BigDecimal;

public record ProfessorResponseDTO(

        Long id,
        String nome,
        String email,
        String perfil,
        BigDecimal valorHoraAula

) {
}