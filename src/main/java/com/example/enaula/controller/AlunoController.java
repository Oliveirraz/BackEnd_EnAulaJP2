package com.example.enaula.controller;

import com.example.enaula.dto.AlunoRequestDTO;
import com.example.enaula.dto.AlunoResponseDTO;
import com.example.enaula.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/alunos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    // Criar aluno
    @PostMapping(consumes = "multipart/form-data")
    public AlunoResponseDTO criarAluno(
            @RequestPart("aluno") @Valid AlunoRequestDTO dto,
            @RequestPart(value = "foto", required = false) MultipartFile foto) {

        return alunoService.criarAluno(dto, foto);
    }

    // Buscar aluno por ID
    @GetMapping("/{id}")
    public AlunoResponseDTO buscarAlunoPorId(
            @PathVariable Long id) {

        return alunoService.buscarAlunoPorId(id);
    }

    // Atualizar aluno
    @PutMapping("/{id}")
    public AlunoResponseDTO atualizarAluno(
            @PathVariable Long id,
            @Valid @RequestBody AlunoRequestDTO dto) {

        return alunoService.atualizarAluno(id, dto);
    }

    // Deletar aluno
    @DeleteMapping("/{id}")
    public void deletarAluno(@PathVariable Long id) {

        alunoService.deletarAluno(id);
    }

    // Enviar/trocar foto de perfil
    @PostMapping(value = "/{id}/foto", consumes = "multipart/form-data")
    public AlunoResponseDTO enviarFoto(
            @PathVariable Long id,
            @RequestParam("arquivo") MultipartFile arquivo) {

        return alunoService.enviarFoto(id, arquivo);
    }

    // Remover foto de perfil
    @DeleteMapping("/{id}/foto")
    public AlunoResponseDTO removerFoto(@PathVariable Long id) {

        return alunoService.removerFoto(id);
    }
}