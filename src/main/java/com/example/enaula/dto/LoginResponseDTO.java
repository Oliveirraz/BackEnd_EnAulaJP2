package com.example.enaula.dto;

import java.util.List;

public record LoginResponseDTO(
        String token,
        String tipo,
        Long id,
        String nome,
        String email,
        List<MateriaResponseDTO> materias
) {
}