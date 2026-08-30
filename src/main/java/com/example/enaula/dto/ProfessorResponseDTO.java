package com.example.enaula.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProfessorResponseDTO(Long id, String nome, String email, String perfil, BigDecimal valorHoraAula, String foto) {
}