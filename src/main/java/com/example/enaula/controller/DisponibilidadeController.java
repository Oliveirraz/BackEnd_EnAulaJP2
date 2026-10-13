package com.example.enaula.controller;

import com.example.enaula.dto.DisponibilidadeRequestDTO;
import com.example.enaula.dto.DisponibilidadeResponseDTO;
import com.example.enaula.entity.Professor;
import com.example.enaula.service.DisponibilidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/disponibilidades")
@RequiredArgsConstructor
public class DisponibilidadeController {

    private final DisponibilidadeService disponibilidadeService;


    @PostMapping
    public ResponseEntity<DisponibilidadeResponseDTO> cadastrar(
            @Valid @RequestBody DisponibilidadeRequestDTO dto,
            @AuthenticationPrincipal Professor professor
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        disponibilidadeService.cadastrar(
                                dto,
                                professor
                        )
                );
    }


    @GetMapping
    public ResponseEntity<List<DisponibilidadeResponseDTO>> listar(
            @AuthenticationPrincipal Professor professor
    ) {

        return ResponseEntity.ok(
                disponibilidadeService.listar(professor)
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<DisponibilidadeResponseDTO> buscarPorId(
            @PathVariable Long id,
            @AuthenticationPrincipal Professor professor
    ) {

        return ResponseEntity.ok(
                disponibilidadeService.buscarPorId(
                        id,
                        professor
                )
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<DisponibilidadeResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody DisponibilidadeRequestDTO dto,
            @AuthenticationPrincipal Professor professor
    ) {

        return ResponseEntity.ok(
                disponibilidadeService.atualizar(
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

        disponibilidadeService.deletar(
                id,
                professor
        );

        return ResponseEntity
                .noContent()
                .build();
    }
}