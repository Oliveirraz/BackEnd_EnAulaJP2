
package com.example.enaula.controller;

import com.example.enaula.dto.ProfessorRequestDTO;
import com.example.enaula.dto.ProfessorResponseDTO;
import com.example.enaula.service.ProfessorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/professores")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    // CADASTRAR PROFESSOR COM FOTO OPCIONAL
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ProfessorResponseDTO> cadastrar(
            @RequestPart("professor")
            @Valid ProfessorRequestDTO dto,

            @RequestPart(value = "foto", required = false)
            MultipartFile foto
    ) {

        ProfessorResponseDTO professor =
                professorService.cadastrarProfessor(dto, foto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(professor);
    }

    // LISTAR TODOS OS PROFESSORES
    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>> listar() {

        List<ProfessorResponseDTO> professores =
                professorService.listarProfessores();

        return ResponseEntity.ok(professores);
    }

    // BUSCAR PROFESSOR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> buscarPorId(
            @PathVariable Long id
    ) {

        ProfessorResponseDTO professor =
                professorService.buscarPorId(id);

        return ResponseEntity.ok(professor);
    }

    // ATUALIZAR DADOS DO PROFESSOR
    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProfessorRequestDTO dto
    ) {

        ProfessorResponseDTO professor =
                professorService.atualizarProfessor(id, dto);

        return ResponseEntity.ok(professor);
    }

    // EXCLUIR PROFESSOR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id
    ) {

        professorService.deletarProfessor(id);

        return ResponseEntity.noContent().build();
    }

    // ENVIAR OU TROCAR FOTO DO PROFESSOR
    @PostMapping(
            value = "/{id}/foto",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<ProfessorResponseDTO> enviarFoto(
            @PathVariable Long id,
            @RequestParam("arquivo") MultipartFile arquivo
    ) {

        ProfessorResponseDTO professor =
                professorService.enviarFoto(id, arquivo);

        return ResponseEntity.ok(professor);
    }

    // REMOVER FOTO DO PROFESSOR
    @DeleteMapping("/{id}/foto")
    public ResponseEntity<ProfessorResponseDTO> removerFoto(
            @PathVariable Long id
    ) {

        ProfessorResponseDTO professor =
                professorService.removerFoto(id);

        return ResponseEntity.ok(professor);
    }
}
