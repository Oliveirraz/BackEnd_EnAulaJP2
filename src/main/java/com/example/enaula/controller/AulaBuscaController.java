package com.example.enaula.controller;

import com.example.enaula.dto.AulaBuscaResponseDTO;
import com.example.enaula.dto.AulaBuscaResultadoDTO;
import com.example.enaula.entity.Modalidade;
import com.example.enaula.service.AulaBuscaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.enaula.entity.Aluno;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/aulas")
@RequiredArgsConstructor
public class AulaBuscaController {

    private final AulaBuscaService aulaBuscaService;

    @GetMapping("/buscar")
    public ResponseEntity<AulaBuscaResultadoDTO> buscar(

            @RequestParam(required = false)
            Long materiaId,

            @RequestParam(required = false)
            Long professorId,

            @RequestParam(required = false)
            BigDecimal valorMinimo,

            @RequestParam(required = false)
            BigDecimal valorMaximo,

            @RequestParam(required = false)
            Modalidade modalidade,

            @RequestParam(required = false)
            Double raioKm,

            @AuthenticationPrincipal UserDetails usuario

    ) {

        if (!(usuario instanceof Aluno aluno)) {
            throw new AccessDeniedException(
                    "Somente alunos podem buscar aulas."
            );
        }

        List<AulaBuscaResponseDTO> resultados =
                aulaBuscaService.buscar(
                        materiaId,
                        professorId,
                        valorMinimo,
                        valorMaximo,
                        modalidade,
                        aluno.getLatitude(),
                        aluno.getLongitude(),
                        raioKm
                );

        String mensagem = resultados.isEmpty()
                ? "Nenhuma aula encontrada para os filtros aplicados."
                : "Aulas encontradas com sucesso.";

        AulaBuscaResultadoDTO resposta =
                new AulaBuscaResultadoDTO(
                        mensagem,
                        resultados
                );

        return ResponseEntity.ok(resposta);
    }
}