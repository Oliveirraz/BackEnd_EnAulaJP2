
package com.example.enaula.service;


import com.example.enaula.dto.MatriculaResponseDTO;
import com.example.enaula.dto.NotificacaoResponseDTO;
import com.example.enaula.entity.*;
import com.example.enaula.exception.ResourceNotFoundException;
import com.example.enaula.repository.AlunoRepository;
import com.example.enaula.repository.AulaRepository;
import com.example.enaula.repository.MatriculaRepository;
import com.example.enaula.repository.NotificacaoRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AulaRepository aulaRepository;
    private final AlunoRepository alunoRepository;
    private final NotificacaoRepository notificacaoRepository;
    private final EmailService emailService;

    // Aluno solicita matrícula em uma aula.
    @Transactional
    public MatriculaResponseDTO solicitar(
            Long aulaId,
            Aluno alunoAutenticado) {

        if (alunoAutenticado == null
                || alunoAutenticado.getId() == null) {
            throw new AccessDeniedException(
                    "Apenas alunos autenticados podem solicitar matrícula"
            );
        }

        Aluno aluno = alunoRepository
                .findById(alunoAutenticado.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Aluno não encontrado")
                );

        Aula aula = aulaRepository.findById(aulaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Aula não encontrada")
                );

        if (matriculaRepository.existsByAulaAndAluno(aula, aluno)) {
            throw new IllegalArgumentException(
                    "Já existe uma solicitação de matrícula para esta aula"
            );
        }

        Matricula matricula = new Matricula();
        matricula.setAula(aula);
        matricula.setAluno(aluno);
        matricula.setStatus(StatusMatricula.PENDENTE);

        return toDTO(matriculaRepository.save(matricula));
    }

    // Monitor consulta solicitações pendentes da própria aula.
    @Transactional(readOnly = true)
    public List<MatriculaResponseDTO> listarPendentes(
            Long aulaId,
            Professor monitor) {

        validarMonitorAutenticado(monitor);

        Aula aula = buscarAulaDoMonitor(aulaId, monitor);

        return matriculaRepository
                .findAllByAulaAndStatusOrderByDataSolicitacaoAsc(
                        aula,
                        StatusMatricula.PENDENTE
                )
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // Monitor aceita a matrícula.
    @Transactional
    public MatriculaResponseDTO aceitar(
            Long matriculaId,
            Professor monitor) {

        validarMonitorAutenticado(monitor);

        Matricula matricula =
                buscarMatriculaDoMonitor(matriculaId, monitor);

        validarPendente(matricula);

        matricula.setStatus(StatusMatricula.ACEITA);
        matricula.setDataDecisao(LocalDateTime.now());

        Matricula salva = matriculaRepository.save(matricula);

        String materia = salva.getAula().getMateria().getNome();

        String mensagem = "Sua matrícula na aula de "
                + materia + " foi aceita.";

        criarNotificacao(salva, mensagem);

        emailService.enviarEmail(
                salva.getAluno().getEmail(),
                "Matrícula aceita - EnAula",
                "Olá, " + salva.getAluno().getNome() + "!\n\n"
                        + mensagem + "\n\n"
                        + "Acesse o EnAula para consultar suas aulas."
        );

        return toDTO(salva);
    }

    // Monitor recusa a matrícula e informa o motivo.
    @Transactional
    public MatriculaResponseDTO recusar(
            Long matriculaId,
            String motivo,
            Professor monitor) {

        validarMonitorAutenticado(monitor);

        Matricula matricula =
                buscarMatriculaDoMonitor(matriculaId, monitor);

        validarPendente(matricula);

        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException(
                    "Informe o motivo da recusa"
            );
        }

        String motivoTratado = motivo.trim();

        if (motivoTratado.length() > 500) {
            throw new IllegalArgumentException(
                    "O motivo deve ter no máximo 500 caracteres"
            );
        }

        matricula.setStatus(StatusMatricula.RECUSADA);
        matricula.setMotivoRecusa(motivoTratado);
        matricula.setDataDecisao(LocalDateTime.now());

        Matricula salva = matriculaRepository.save(matricula);

        String materia = salva.getAula().getMateria().getNome();

        String mensagem = "Sua matrícula na aula de "
                + materia
                + " foi recusada. Motivo: "
                + salva.getMotivoRecusa();

        criarNotificacao(salva, mensagem);

        emailService.enviarEmail(
                salva.getAluno().getEmail(),
                "Matrícula recusada - EnAula",
                "Olá, " + salva.getAluno().getNome() + "!\n\n"
                        + mensagem + "\n\n"
                        + "Acesse o EnAula para consultar suas solicitações."
        );

        return toDTO(salva);
    }

    // Aluno consulta as próprias notificações.
    @Transactional(readOnly = true)
    public List<NotificacaoResponseDTO> minhasNotificacoes(
            Aluno alunoAutenticado) {

        if (alunoAutenticado == null
                || alunoAutenticado.getId() == null) {
            throw new AccessDeniedException(
                    "Apenas alunos autenticados podem consultar notificações"
            );
        }

        Aluno aluno = alunoRepository
                .findById(alunoAutenticado.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Aluno não encontrado")
                );

        return notificacaoRepository
                .findAllByAlunoOrderByDataCriacaoDesc(aluno)
                .stream()
                .map(n -> new NotificacaoResponseDTO(
                        n.getId(),
                        n.getMensagem(),
                        n.getDataCriacao(),
                        n.isLida(),
                        n.getMatricula().getId()
                ))
                .toList();
    }

    private void validarMonitorAutenticado(Professor monitor) {
        if (monitor == null || monitor.getId() == null) {
            throw new AccessDeniedException(
                    "Apenas o monitor autenticado pode executar esta operação"
            );
        }
    }

    private Aula buscarAulaDoMonitor(
            Long aulaId,
            Professor monitor) {

        Aula aula = aulaRepository.findById(aulaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Aula não encontrada")
                );

        if (!aula.getProfessor().getId().equals(monitor.getId())) {
            throw new AccessDeniedException(
                    "Você não é o monitor responsável por esta aula"
            );
        }

        return aula;
    }

    private Matricula buscarMatriculaDoMonitor(
            Long matriculaId,
            Professor monitor) {

        Matricula matricula = matriculaRepository
                .findById(matriculaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Solicitação de matrícula não encontrada"
                        )
                );

        if (!matricula.getAula().getProfessor()
                .getId().equals(monitor.getId())) {
            throw new AccessDeniedException(
                    "Você não é o monitor responsável por esta matrícula"
            );
        }

        return matricula;
    }

    private void validarPendente(Matricula matricula) {
        if (matricula.getStatus() != StatusMatricula.PENDENTE) {
            throw new IllegalArgumentException(
                    "Esta solicitação já foi decidida"
            );
        }
    }

    private void criarNotificacao(
            Matricula matricula,
            String mensagem) {

        Notificacao notificacao = new Notificacao();

        notificacao.setAluno(matricula.getAluno());
        notificacao.setMatricula(matricula);
        notificacao.setMensagem(mensagem);

        notificacaoRepository.save(notificacao);
    }

    private MatriculaResponseDTO toDTO(Matricula matricula) {
        return new MatriculaResponseDTO(
                matricula.getId(),
                matricula.getAula().getId(),
                matricula.getAula().getMateria().getNome(),
                matricula.getAluno().getId(),
                matricula.getAluno().getNome(),
                matricula.getAluno().getEmail(),
                matricula.getStatus(),
                matricula.getMotivoRecusa(),
                matricula.getDataSolicitacao(),
                matricula.getDataDecisao()
        );
    }


    // Aluno cancela a própria matrícula.
    @Transactional
    public String cancelar(
            Long matriculaId,
            Aluno alunoAutenticado,
            int horasAntecedencia) {

        // Verifica se existe um aluno autenticado.
        if (alunoAutenticado == null
                || alunoAutenticado.getId() == null) {

            throw new AccessDeniedException(
                    "Apenas alunos autenticados podem cancelar matrícula"
            );
        }

        // Busca o aluno no banco de dados.
        Aluno aluno = alunoRepository
                .findById(alunoAutenticado.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Aluno não encontrado"
                        )
                );

        // Busca a matrícula pertencente especificamente a esse aluno.
        Matricula matricula = matriculaRepository
                .findByIdAndAluno(matriculaId, aluno)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Matrícula não encontrada para este aluno"
                        )
                );

        // Só permite cancelar matrículas pendentes ou aceitas.
        if (matricula.getStatus() != StatusMatricula.PENDENTE
                && matricula.getStatus() != StatusMatricula.ACEITA) {

            throw new IllegalArgumentException(
                    "Somente matrículas pendentes ou aceitas podem ser canceladas"
            );
        }

        // Obtém a data e o horário de início da aula.
        Aula aula = matricula.getAula();

        LocalDateTime inicioAula = LocalDateTime.of(
                aula.getData(),
                aula.getHorario()
        );

        // Calcula o limite para cancelamento com possibilidade de reembolso.
        LocalDateTime limiteCancelamento =
                inicioAula.minusHours(horasAntecedencia);

        LocalDateTime agora = LocalDateTime.now();

        // Não permite cancelar depois que a aula começou.
        if (!agora.isBefore(inicioAula)) {
            throw new IllegalArgumentException(
                    "Não é possível cancelar a matrícula depois do início da aula"
            );
        }

        // Verifica se o cancelamento está dentro do prazo.
        boolean dentroDoPrazo =
                !agora.isAfter(limiteCancelamento);

        // Atualiza o status da matrícula.
        matricula.setStatus(StatusMatricula.CANCELADA);
        matricula.setDataDecisao(agora);

        Matricula matriculaSalva =
                matriculaRepository.save(matricula);

        // Prepara a mensagem para o aluno.
        String nomeMateria = aula.getMateria().getNome();

        String mensagem;

        if (dentroDoPrazo) {
            mensagem = "Sua matrícula na aula de "
                    + nomeMateria
                    + " foi cancelada dentro do prazo permitido.";
        } else {
            mensagem = "Sua matrícula na aula de "
                    + nomeMateria
                    + " foi cancelada fora do prazo de "
                    + horasAntecedencia
                    + " horas. Este cancelamento não gera reembolso.";
        }

        // Registra uma notificação para o aluno.
        criarNotificacao(matriculaSalva, mensagem);

        // Envia um e-mail informativo ao aluno.
        emailService.enviarEmail(
                aluno.getEmail(),
                "Cancelamento de matrícula - EnAula",
                "Olá, " + aluno.getNome() + "!\n\n"
                        + mensagem + "\n\n"
                        + "Acesse o EnAula para consultar suas aulas."
        );

        return mensagem;
    }

}