
package com.example.enaula.controller;

import com.example.enaula.dto.MatriculaResponseDTO;
import com.example.enaula.dto.NotificacaoResponseDTO;
import com.example.enaula.dto.RecusaMatriculaRequestDTO;
import com.example.enaula.entity.Aluno;
import com.example.enaula.entity.Professor;
import com.example.enaula.service.MatriculaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
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

    // Prazo configurável. O valor padrão é 24 horas.
    @Value("${enaula.cancelamento.horas-antecedencia:24}")
    private int horasAntecedenciaParaCancelar;

    // Aluno solicita matrícula.
    @PostMapping("/aulas/{aulaId}/matriculas")
    public ResponseEntity<MatriculaResponseDTO> solicitar(
            @PathVariable Long aulaId,
            @AuthenticationPrincipal Aluno aluno) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(matriculaService.solicitar(aulaId, aluno));
    }

    // Professor lista as solicitações pendentes da própria aula.
    @GetMapping("/aulas/{aulaId}/matriculas/pendentes")
    public ResponseEntity<List<MatriculaResponseDTO>> listarPendentes(
            @PathVariable Long aulaId,
            @AuthenticationPrincipal Professor professor) {

        return ResponseEntity.ok(
                matriculaService.listarPendentes(aulaId, professor)
        );
    }

    // Professor aceita uma solicitação.
    @PatchMapping("/matriculas/{matriculaId}/aceitar")
    public ResponseEntity<MatriculaResponseDTO> aceitar(
            @PathVariable Long matriculaId,
            @AuthenticationPrincipal Professor professor) {

        return ResponseEntity.ok(
                matriculaService.aceitar(matriculaId, professor)
        );
    }

    // Professor recusa uma solicitação.
    @PatchMapping("/matriculas/{matriculaId}/recusar")
    public ResponseEntity<MatriculaResponseDTO> recusar(
            @PathVariable Long matriculaId,
            @Valid @RequestBody RecusaMatriculaRequestDTO dto,
            @AuthenticationPrincipal Professor professor) {

        return ResponseEntity.ok(
                matriculaService.recusar(
                        matriculaId,
                        dto.motivo(),
                        professor
                )
        );
    }

    // Aluno cancela a própria matrícula.
    @PatchMapping("/matriculas/{matriculaId}/cancelar")
    public ResponseEntity<String> cancelar(
            @PathVariable Long matriculaId,
            @AuthenticationPrincipal Aluno aluno) {

        String mensagem = matriculaService.cancelar(
                matriculaId,
                aluno,
                horasAntecedenciaParaCancelar
        );

        return ResponseEntity.ok(mensagem);
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
