package com.example.enaula.controller;

import com.example.enaula.dto.MateriaRequestDTO;
import com.example.enaula.dto.MateriaResponseDTO;
import com.example.enaula.entity.Professor;
import com.example.enaula.service.MateriaService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/materias")
@RequiredArgsConstructor
public class MateriaController {

    private final MateriaService materiaService;


    // Cadastrar a materia
    // Post
    @PostMapping
    public ResponseEntity<MateriaResponseDTO> cadastrar(
            @Valid
            @RequestBody MateriaRequestDTO dto,
            @AuthenticationPrincipal Professor professor
    ) {

        MateriaResponseDTO materia =
                materiaService.cadastrarMateria(dto, professor);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(materia);
    }


    // Listar todas as matérias do professor logado
    // GET
    @GetMapping
    public ResponseEntity<List<MateriaResponseDTO>> listar(
            @AuthenticationPrincipal Professor professor
    ) {

        List<MateriaResponseDTO> materias =
                materiaService.listarMaterias(professor);

        return ResponseEntity.ok(materias);
    }

    // Buscar só pelo id
    // GET

    @GetMapping("/{id}")
    public ResponseEntity<MateriaResponseDTO> buscarPorId(
            @PathVariable Long id,
            @AuthenticationPrincipal Professor professor
    ) {

        MateriaResponseDTO materia =
                materiaService.buscarPorId(id, professor);

        return ResponseEntity.ok(materia);
    }

    // Atualizar a matéria
    // Put

    @PutMapping("/{id}")
    public ResponseEntity<MateriaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid
            @RequestBody MateriaRequestDTO dto,
            @AuthenticationPrincipal Professor professor
    ) {

        MateriaResponseDTO materia =
                materiaService.atualizarMateria(id, dto, professor);

        return ResponseEntity.ok(materia);
    }


    // Deletar a matéria
    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id,
            @AuthenticationPrincipal Professor professor
    ) {

        materiaService.deletarMateria(id, professor);

        return ResponseEntity
                .noContent()
                .build();
    }
}
