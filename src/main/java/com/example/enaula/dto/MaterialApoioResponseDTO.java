package com.example.enaula.dto;

import java.time.LocalDateTime;

public record MaterialApoioResponseDTO(
        Long id,
        String nomeOriginal,
        String tipoConteudo,
        Long tamanhoBytes,
        LocalDateTime dataEnvio,
        Long aulaId,
        Long professorId,
        String professorNome
) {
}
