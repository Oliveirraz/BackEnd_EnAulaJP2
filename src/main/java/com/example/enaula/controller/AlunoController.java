package com.example.enaula.controller;

import com.example.enaula.dto.AlunoRequestDTO;
import com.example.enaula.dto.AlunoResponseDTO;
import com.example.enaula.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alunos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    // Criar aluno
    @PostMapping
    public AlunoResponseDTO criarAluno(@Valid @RequestBody AlunoRequestDTO dto) {
        return alunoService.criarAluno(dto);
    }

    // Buscar aluno por ID
    @GetMapping("/{id}")
    public AlunoResponseDTO buscarAlunoPorId(@PathVariable Long id) {
        return alunoService.buscarAlunoPorId(id);
    }

    // Atualizar aluno
    @PutMapping("/{id}")
    public AlunoResponseDTO atualizarAluno(
            @PathVariable Long id,
            @RequestBody AlunoRequestDTO dto) {

        return alunoService.atualizarAluno(id, dto);
    }

    // Deletar aluno
    @DeleteMapping("/{id}")
    public void deletarAluno(@PathVariable Long id) {
        alunoService.deletarAluno(id);
    }

}
