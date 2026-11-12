package com.example.enaula.controller;

import com.example.enaula.dto.AlunoRequestDTO;
import com.example.enaula.dto.AlunoResponseDTO;
import com.example.enaula.dto.AlunoLocalizacaoRequestDTO;
import com.example.enaula.dto.HistoricoAulaResponseDTO;

import com.example.enaula.entity.Aluno;

import com.example.enaula.service.AlunoService;
import com.example.enaula.service.HistoricoAulaService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    private final HistoricoAulaService historicoAulaService;

    // Criar aluno
    @PostMapping(consumes = "multipart/form-data")
    public AlunoResponseDTO criarAluno(
            @RequestPart("aluno") @Valid AlunoRequestDTO dto,
            @RequestPart(value = "foto", required = false)
            MultipartFile foto
    ) {

        return alunoService.criarAluno(dto, foto);
    }

    // ==========================================
    // HU024 - CONSULTAR HISTÓRICO DE AULAS
    // ==========================================

    @GetMapping("/meu-historico")
    public List<HistoricoAulaResponseDTO> consultarHistorico(
            @AuthenticationPrincipal UserDetails usuario
    ) {

        // Verifica se o usuário autenticado é um aluno.
        if (!(usuario instanceof Aluno aluno)) {

            throw new AccessDeniedException(
                    "Somente alunos podem consultar o histórico de aulas."
            );
        }

        // Consulta somente o histórico do aluno autenticado.
        return historicoAulaService.consultar(aluno);
    }

    // Buscar aluno por ID
    @GetMapping("/{id}")
    @PreAuthorize("@donoDoPerfil.ehAluno(authentication, #id)")
    public AlunoResponseDTO buscarAlunoPorId(
            @PathVariable Long id
    ) {

        return alunoService.buscarAlunoPorId(id);
    }

    // Atualizar aluno
    @PutMapping("/{id}")
    @PreAuthorize("@donoDoPerfil.ehAluno(authentication, #id)")
    public AlunoResponseDTO atualizarAluno(
            @PathVariable Long id,
            @Valid @RequestBody AlunoRequestDTO dto
    ) {

        return alunoService.atualizarAluno(id, dto);
    }

    // Deletar aluno
    @DeleteMapping("/{id}")
    @PreAuthorize("@donoDoPerfil.ehAluno(authentication, #id)")
    public void deletarAluno(
            @PathVariable Long id
    ) {

        alunoService.deletarAluno(id);
    }

    // Enviar/trocar foto de perfil
    @PostMapping(
            value = "/{id}/foto",
            consumes = "multipart/form-data"
    )
    @PreAuthorize("@donoDoPerfil.ehAluno(authentication, #id)")
    public AlunoResponseDTO enviarFoto(
            @PathVariable Long id,
            @RequestParam("arquivo") MultipartFile arquivo
    ) {

        return alunoService.enviarFoto(id, arquivo);
    }

    // Remover foto de perfil
    @DeleteMapping("/{id}/foto")
    @PreAuthorize("@donoDoPerfil.ehAluno(authentication, #id)")
    public AlunoResponseDTO removerFoto(
            @PathVariable Long id
    ) {

        return alunoService.removerFoto(id);
    }

    // Atualizar localização do aluno autenticado
    @PutMapping("/minha-localizacao")
    @PreAuthorize("@donoDoPerfil.ehAluno(authentication, #id)")
    public AlunoResponseDTO atualizarMinhaLocalizacao(
            @AuthenticationPrincipal UserDetails usuario,
            @Valid @RequestBody AlunoLocalizacaoRequestDTO dto
    ) {

        if (!(usuario instanceof Aluno)) {

            throw new AccessDeniedException(
                    "Somente alunos podem atualizar sua localização."
            );
        }

        return alunoService.atualizarLocalizacao(
                usuario.getUsername(),
                dto
        );
    }
}