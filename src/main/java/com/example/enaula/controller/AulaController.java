package com.example.enaula.controller;

import com.example.enaula.dto.AulaRequestDTO;
import com.example.enaula.dto.AulaResponseDTO;
import com.example.enaula.entity.Professor;
import com.example.enaula.service.AulaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
}