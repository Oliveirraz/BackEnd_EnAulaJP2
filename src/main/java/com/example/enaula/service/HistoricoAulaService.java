package com.example.enaula.service;

import com.example.enaula.dto.HistoricoAulaResponseDTO;
import com.example.enaula.entity.Aluno;
import com.example.enaula.entity.Matricula;
import com.example.enaula.entity.StatusMatricula;
import com.example.enaula.exception.ResourceNotFoundException;
import com.example.enaula.repository.AlunoRepository;
import com.example.enaula.repository.MatriculaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricoAulaService {

    private final AlunoRepository alunoRepository;

    private final MatriculaRepository matriculaRepository;

    @Value("${enaula.gravacao.retencao-dias:30}")
    private long diasRetencaoGravacao;

    /**
     * Consulta o histórico de aulas do aluno autenticado.
     */
    @Transactional(readOnly = true)
    public List<HistoricoAulaResponseDTO> consultar(
            Aluno alunoAutenticado
    ) {

        if (alunoAutenticado == null
                || alunoAutenticado.getId() == null) {

            throw new AccessDeniedException(
                    "Apenas alunos autenticados podem consultar o histórico."
            );
        }

        // Busca o aluno no banco.
        Aluno aluno = alunoRepository.findById(
                alunoAutenticado.getId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Aluno não encontrado."
                )
        );

        LocalDateTime agora = LocalDateTime.now();

        // Busca as matrículas do aluno.
        // O Repository já realiza a ordenação decrescente.
        return matriculaRepository.buscarHistoricoDoAluno(aluno)
                .stream()

                // Mantém aulas canceladas e aulas concluídas.
                .filter(matricula -> {

                    if (matricula.getStatus()
                            == StatusMatricula.CANCELADA) {

                        return true;
                    }

                    var aula = matricula.getAula();

                    LocalDateTime fimAula = LocalDateTime.of(
                            aula.getData(),
                            aula.getHorario()
                    ).plusHours(aula.getDuracao());

                    return !fimAula.isAfter(agora);
                })

                // Converte para DTO.
                .map(matricula ->
                        converterParaDTO(matricula, agora)
                )

                .toList();
    }

    /**
     * Converte uma matrícula em informações do histórico.
     */
    private HistoricoAulaResponseDTO converterParaDTO(
            Matricula matricula,
            LocalDateTime agora
    ) {

        var aula = matricula.getAula();

        // Define o status apresentado no histórico.
        String status;

        if (matricula.getStatus()
                == StatusMatricula.CANCELADA) {

            status = "CANCELADA";

        } else {

            status = "CONCLUIDA";
        }

        // Calcula o início da retenção.
        // Prioriza a data real de envio da gravação.
        LocalDateTime inicioRetencao =
                aula.getGravacaoDataEnvio() != null
                        ? aula.getGravacaoDataEnvio()
                        : LocalDateTime.of(
                        aula.getData(),
                        aula.getHorario()
                ).plusHours(aula.getDuracao());

        // Calcula o limite de retenção.
        LocalDateTime limiteRetencao =
                inicioRetencao.plusDays(diasRetencaoGravacao);

        // Calcula o término da aula.
        LocalDateTime fimAula = LocalDateTime.of(
                aula.getData(),
                aula.getHorario()
        ).plusHours(aula.getDuracao());

        // Verifica se a gravação está disponível.
        boolean gravacaoDisponivel =
                matricula.getStatus() == StatusMatricula.ACEITA

                        && aula.getGravacaoChaveObjeto() != null

                        && !aula.getGravacaoChaveObjeto().isBlank()

                        && !agora.isBefore(fimAula)

                        && !agora.isAfter(limiteRetencao);

        // Só disponibiliza a URL quando todas as condições
        // forem atendidas.
        String urlGravacao = gravacaoDisponivel
                ? "/api/aulas/" + aula.getId() + "/gravacao"
                : null;

        return new HistoricoAulaResponseDTO(

                aula.getId(),

                aula.getData(),

                aula.getHorario(),

                aula.getMateria().getNome(),

                aula.getProfessor().getNome(),

                status,

                gravacaoDisponivel,

                urlGravacao
        );
    }
}