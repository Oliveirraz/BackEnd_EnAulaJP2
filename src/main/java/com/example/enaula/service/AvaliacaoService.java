package com.example.enaula.service;

import com.example.enaula.dto.AvaliacaoRequestDTO;
import com.example.enaula.dto.AvaliacaoResponseDTO;

import com.example.enaula.entity.Aluno;
import com.example.enaula.entity.Aula;
import com.example.enaula.entity.Avaliacao;
import com.example.enaula.entity.Matricula;
import com.example.enaula.entity.StatusMatricula;

import com.example.enaula.exception.ResourceNotFoundException;

import com.example.enaula.repository.AlunoRepository;
import com.example.enaula.repository.AulaRepository;
import com.example.enaula.repository.AvaliacaoRepository;
import com.example.enaula.repository.MatriculaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    private final AulaRepository aulaRepository;

    private final MatriculaRepository matriculaRepository;

    private final AlunoRepository alunoRepository;


    // =====================================================
    // HU026 - ALUNO AVALIA PROFESSOR APÓS A AULA
    // =====================================================

    @Transactional
    public AvaliacaoResponseDTO avaliarProfessor(
            Long aulaId,
            Aluno alunoAutenticado,
            AvaliacaoRequestDTO dto
    ) {

        // ---------------------------------------------
        // 1. VALIDAR ALUNO AUTENTICADO
        // ---------------------------------------------

        if (alunoAutenticado == null
                || alunoAutenticado.getId() == null) {

            throw new AccessDeniedException(
                    "Somente alunos autenticados podem avaliar professores."
            );
        }

        Aluno aluno = alunoRepository.findById(
                alunoAutenticado.getId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Aluno não encontrado."
                )
        );


        // ---------------------------------------------
        // 2. BUSCAR A AULA
        // ---------------------------------------------

        Aula aula = aulaRepository.findById(aulaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Aula não encontrada."
                        )
                );


        // ---------------------------------------------
        // 3. VERIFICAR MATRÍCULA ACEITA
        // ---------------------------------------------

        Matricula matricula = matriculaRepository
                .findByAulaAndAlunoAndStatus(
                        aula,
                        aluno,
                        StatusMatricula.ACEITA
                )
                .orElseThrow(() ->
                        new AccessDeniedException(
                                "Você precisa possuir uma matrícula aceita "
                                        + "para avaliar esta aula."
                        )
                );


        // ---------------------------------------------
        // 4. VERIFICAR SE A AULA FOI CONCLUÍDA
        // ---------------------------------------------

        LocalDateTime inicioAula = LocalDateTime.of(
                aula.getData(),
                aula.getHorario()
        );

        LocalDateTime fimAula = inicioAula.plusHours(
                aula.getDuracao()
        );

        if (LocalDateTime.now().isBefore(fimAula)) {

            throw new IllegalArgumentException(
                    "A avaliação só pode ser realizada após "
                            + "a conclusão da aula."
            );
        }


        // ---------------------------------------------
        // 5. IMPEDIR AVALIAÇÃO DUPLICADA
        // ---------------------------------------------

        if (avaliacaoRepository.existsByAulaAndAluno(
                aula,
                aluno
        )) {

            throw new IllegalArgumentException(
                    "Você já avaliou esta aula."
            );
        }


        // ---------------------------------------------
        // 6. VALIDAR NOTA
        // ---------------------------------------------

        if (dto.nota() == null
                || dto.nota() < 1
                || dto.nota() > 5) {

            throw new IllegalArgumentException(
                    "A nota deve estar entre 1 e 5 estrelas."
            );
        }


        // ---------------------------------------------
        // 7. VALIDAR JUSTIFICATIVA
        // ---------------------------------------------

        String justificativa = dto.justificativa();

        if (dto.nota() < 3) {

            if (justificativa == null
                    || justificativa.isBlank()) {

                throw new IllegalArgumentException(
                        "Para notas inferiores a 3 estrelas, "
                                + "é obrigatório informar uma justificativa."
                );
            }

            justificativa = justificativa.trim();

        } else if (justificativa != null) {

            justificativa = justificativa.trim();

            if (justificativa.isBlank()) {
                justificativa = null;
            }
        }


        // ---------------------------------------------
        // 8. CRIAR A AVALIAÇÃO
        // ---------------------------------------------

        Avaliacao avaliacao = new Avaliacao();

        avaliacao.setAula(aula);

        avaliacao.setAluno(aluno);

        avaliacao.setProfessor(aula.getProfessor());

        avaliacao.setNota(dto.nota());

        avaliacao.setJustificativa(justificativa);

        avaliacao.setDataAvaliacao(LocalDateTime.now());


        // ---------------------------------------------
        // 9. SALVAR NO BANCO
        // ---------------------------------------------

        Avaliacao salva = avaliacaoRepository.save(avaliacao);

        return converterParaDTO(salva);
    }


    // =====================================================
    // HU026 - CONSULTAR A PRÓPRIA AVALIAÇÃO
    // =====================================================

    @Transactional(readOnly = true)
    public AvaliacaoResponseDTO consultarMinhaAvaliacao(
            Long aulaId,
            Aluno alunoAutenticado
    ) {

        if (alunoAutenticado == null
                || alunoAutenticado.getId() == null) {

            throw new AccessDeniedException(
                    "Somente alunos autenticados podem consultar avaliações."
            );
        }

        Aluno aluno = alunoRepository.findById(
                alunoAutenticado.getId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Aluno não encontrado."
                )
        );

        Aula aula = aulaRepository.findById(aulaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Aula não encontrada."
                        )
                );

        return avaliacaoRepository.findByAulaAndAluno(
                        aula,
                        aluno
                )
                .map(this::converterParaDTO)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Você ainda não realizou uma avaliação para esta aula."
                        )
                );
    }


    // =====================================================
    // CONVERTER ENTIDADE PARA DTO
    // =====================================================

    private AvaliacaoResponseDTO converterParaDTO(
            Avaliacao avaliacao
    ) {

        return new AvaliacaoResponseDTO(

                avaliacao.getId(),

                avaliacao.getAula().getId(),

                avaliacao.getAluno().getId(),

                avaliacao.getAluno().getNome(),

                avaliacao.getProfessor().getId(),

                avaliacao.getProfessor().getNome(),

                avaliacao.getAula().getMateria().getNome(),

                avaliacao.getNota(),

                avaliacao.getJustificativa(),

                avaliacao.getDataAvaliacao()
        );
    }
}