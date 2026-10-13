
package com.example.enaula.controller;

import com.example.enaula.dto.MatriculaResponseDTO;
import com.example.enaula.dto.NotificacaoResponseDTO;
import com.example.enaula.dto.RecusaMatriculaRequestDTO;
import com.example.enaula.entity.Aluno;
import com.example.enaula.entity.Professor;
import com.example.enaula.service.MatriculaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MatriculaController {

    private final MatriculaService matriculaService;

    // Aluno solicita matrícula.
    @PostMapping("/aulas/{aulaId}/matriculas")
    public ResponseEntity<MatriculaResponseDTO> solicitar(
            @PathVariable Long aulaId,
            @AuthenticationPrincipal Aluno aluno) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(matriculaService.solicitar(aulaId, aluno));
    }

    // Monitor lista as solicitações pendentes da própria aula.
    @GetMapping("/aulas/{aulaId}/matriculas/pendentes")
    public ResponseEntity<List<MatriculaResponseDTO>> listarPendentes(
            @PathVariable Long aulaId,
            @AuthenticationPrincipal Professor monitor) {

        return ResponseEntity.ok(
                matriculaService.listarPendentes(aulaId, monitor)
        );
    }

    // Monitor aceita uma solicitação.
    @PatchMapping("/matriculas/{matriculaId}/aceitar")
    public ResponseEntity<MatriculaResponseDTO> aceitar(
            @PathVariable Long matriculaId,
            @AuthenticationPrincipal Professor monitor) {

        return ResponseEntity.ok(
                matriculaService.aceitar(matriculaId, monitor)
        );
    }

    // Monitor recusa uma solicitação.
    @PatchMapping("/matriculas/{matriculaId}/recusar")
    public ResponseEntity<MatriculaResponseDTO> recusar(
            @PathVariable Long matriculaId,
            @Valid @RequestBody RecusaMatriculaRequestDTO dto,
            @AuthenticationPrincipal Professor monitor) {

        return ResponseEntity.ok(
                matriculaService.recusar(
                        matriculaId,
                        dto.motivo(),
                        monitor
                )
        );
    }

    // Aluno consulta as próprias notificações.
    @GetMapping("/notificacoes/minhas")
    public ResponseEntity<List<NotificacaoResponseDTO>> minhasNotificacoes(
            @AuthenticationPrincipal Aluno aluno) {

        return ResponseEntity.ok(
                matriculaService.minhasNotificacoes(aluno)
        );
    }
}
