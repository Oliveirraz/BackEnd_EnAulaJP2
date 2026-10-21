package com.example.enaula.controller;

import com.example.enaula.dto.AulaRequestDTO;
import com.example.enaula.dto.AulaResponseDTO;
import com.example.enaula.entity.Professor;
import com.example.enaula.service.AulaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/aulas")
@RequiredArgsConstructor
public class AulaController {

    private final AulaService aulaService;

    @PostMapping
    public ResponseEntity<AulaResponseDTO> cadastrar(
            @Valid @RequestBody AulaRequestDTO dto,
            @AuthenticationPrincipal Professor professor
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        aulaService.cadastrarAula(
                                dto,
                                professor
                        )
                );
    }

    @GetMapping
    public ResponseEntity<List<AulaResponseDTO>> listar(
            @AuthenticationPrincipal Professor professor
    ) {

        return ResponseEntity.ok(
                aulaService.listarAulas(professor)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AulaResponseDTO> buscarPorId(
            @PathVariable Long id,
            @AuthenticationPrincipal Professor professor
    ) {

        return ResponseEntity.ok(
                aulaService.buscarPorId(
                        id,
                        professor
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AulaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AulaRequestDTO dto,
            @AuthenticationPrincipal Professor professor
    ) {

        return ResponseEntity.ok(
                aulaService.atualizarAula(
                        id,
                        dto,
                        professor
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id,
            @AuthenticationPrincipal Professor professor
    ) {

        aulaService.deletarAula(
                id,
                professor
        );

        return ResponseEntity
                .noContent()
                .build();
    }


    @PostMapping("/{id}/iniciar")
    public ResponseEntity<AulaResponseDTO> iniciar(
            @PathVariable Long id,
            @AuthenticationPrincipal Professor professor
    ) {

        return ResponseEntity.ok(
                aulaService.iniciarAula(id, professor)
        );
    }

    // Método para o professor enviar a aula gravada.
    @PostMapping(value = "/{id}/gravacao", consumes = "multipart/form-data")
    public ResponseEntity<AulaResponseDTO> enviarGravacao(
            @PathVariable Long id,
            @RequestParam("arquivo") MultipartFile arquivo,
            @AuthenticationPrincipal Professor professor
    ) {

        return ResponseEntity.ok(
                aulaService.enviarGravacao(id, professor, arquivo)
        );
    }

    // Método para que o aluno ou o professor possa baixar a aula
    @GetMapping("/{id}/gravacao")
    public void baixarGravacao(
            @PathVariable Long id,
            @AuthenticationPrincipal Object principal,
            HttpServletResponse response
    ) {
        aulaService.baixarGravacao(id, principal, response);
    }
}