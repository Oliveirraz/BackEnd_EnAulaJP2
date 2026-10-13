package com.example.enaula.dto;

import java.time.LocalDateTime;

public record NotificacaoResponseDTO(
        Long id,
        String mensagem,
        LocalDateTime dataCriacao,
        boolean lida,
        Long matriculaId
) {}
