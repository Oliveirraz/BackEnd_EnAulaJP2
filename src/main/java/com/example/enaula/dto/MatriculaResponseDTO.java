package com.example.enaula.dto;
import com.example.enaula.entity.StatusMatricula;
import java.time.LocalDateTime;

public record MatriculaResponseDTO(
        Long id,
        Long aulaId,
        String materia,
        Long alunoId,
        String nomeAluno,
        String emailAluno,
        StatusMatricula status,
        String motivoRecusa,
        LocalDateTime dataSolicitacao,
        LocalDateTime dataDecisao
) {}
