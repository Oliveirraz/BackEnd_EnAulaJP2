package com.example.enaula.controller;

import com.example.enaula.dto.AvaliacaoRequestDTO;
import com.example.enaula.dto.AvaliacaoResponseDTO;

import com.example.enaula.entity.Aluno;

import com.example.enaula.service.AvaliacaoService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/avaliacoes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;


    // =====================================================
    // HU026 - ALUNO AVALIA PROFESSOR
    // =====================================================

    @PostMapping("/aulas/{aulaId}")
    public ResponseEntity<AvaliacaoResponseDTO> avaliarProfessor(

            @PathVariable Long aulaId,

            @AuthenticationPrincipal UserDetails usuario,

            @Valid @RequestBody AvaliacaoRequestDTO dto

    ) {

        // Somente aluno pode avaliar professor.
        if (!(usuario instanceof Aluno aluno)) {

            throw new AccessDeniedException(
                    "Somente alunos podem avaliar professores."
            );
        }

        AvaliacaoResponseDTO resposta =
                avaliacaoService.avaliarProfessor(
                        aulaId,
                        aluno,
                        dto
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resposta);
    }


    // =====================================================
    // HU026 - ALUNO CONSULTA SUA AVALIAÇÃO
    // =====================================================

    @GetMapping("/aulas/{aulaId}/minha")
    public ResponseEntity<AvaliacaoResponseDTO> consultarMinhaAvaliacao(

            @PathVariable Long aulaId,

            @AuthenticationPrincipal UserDetails usuario

    ) {

        if (!(usuario instanceof Aluno aluno)) {

            throw new AccessDeniedException(
                    "Somente alunos podem consultar suas avaliações."
            );
        }

        AvaliacaoResponseDTO resposta =
                avaliacaoService.consultarMinhaAvaliacao(
                        aulaId,
                        aluno
                );

        return ResponseEntity.ok(resposta);
    }
}