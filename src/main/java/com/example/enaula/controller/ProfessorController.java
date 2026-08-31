package com.example.enaula.controller;

import com.example.enaula.RequestDTO.ProfessorRequestDTO;
import com.example.enaula.ResponseDTO.ProfessorResponseDTO;
import com.example.enaula.service.professor.ProfessorService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/professores")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;


    // ==========================
    // CADASTRAR
    // POST
    // ==========================

    @PostMapping
    public ResponseEntity<ProfessorResponseDTO>
    cadastrar(

            @Valid
            @RequestBody ProfessorRequestDTO dto

    ) {

        ProfessorResponseDTO professor =
                professorService
                        .cadastrarProfessor(dto);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(professor);
    }


    // ==========================
    // LISTAR TODOS
    // GET
    // ==========================

    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>>
    listar() {

        List<ProfessorResponseDTO> professores =
                professorService
                        .listarProfessores();


        return ResponseEntity.ok(professores);

    }


    // ==========================
    // BUSCAR POR ID
    // GET
    // ==========================

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO>
    buscarPorId(

            @PathVariable Long id

    ) {

        ProfessorResponseDTO professor =
                professorService
                        .buscarPorId(id);


        return ResponseEntity.ok(professor);

    }


    // ==========================
    // ATUALIZAR
    // PUT
    // ==========================

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO>
    atualizar(

            @PathVariable Long id,

            @Valid
            @RequestBody ProfessorRequestDTO dto

    ) {

        ProfessorResponseDTO professor =
                professorService
                        .atualizarProfessor(
                                id,
                                dto
                        );


        return ResponseEntity.ok(professor);

    }


    // ==========================
    // DELETAR
    // DELETE
    // ==========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deletar(

            @PathVariable Long id

    ) {

        professorService
                .deletarProfessor(id);


        return ResponseEntity
                .noContent()
                .build();

    }

}