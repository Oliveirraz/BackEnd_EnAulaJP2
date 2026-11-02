package com.example.enaula.dto;

import java.util.List;

public record AulaBuscaResultadoDTO(

        String mensagem,

        List<AulaBuscaResponseDTO> resultados

) {
}