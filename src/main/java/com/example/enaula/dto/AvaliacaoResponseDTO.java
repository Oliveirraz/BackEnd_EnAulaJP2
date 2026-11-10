package com.example.enaula.dto;

import java.time.LocalDateTime;

public record AvaliacaoResponseDTO(

        Long id,

        Long aulaId,

        Long alunoId,

        String nomeAluno,

        Long professorId,

        String nomeProfessor,

        String materia,

        Integer nota,

        String justificativa,

        LocalDateTime dataAvaliacao

) {
}
