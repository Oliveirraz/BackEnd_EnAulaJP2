
package com.example.enaula.service;

import com.example.enaula.dto.AulaRequestDTO;
import com.example.enaula.dto.AulaResponseDTO;
import com.example.enaula.dto.daily.DailyRoomResponseDTO;
import com.example.enaula.entity.*;
import com.example.enaula.exception.ResourceNotFoundException;
import com.example.enaula.mapper.AulaMapper;
import com.example.enaula.repository.AulaRepository;
import com.example.enaula.repository.MateriaRepository;
import com.example.enaula.repository.MatriculaRepository;
import com.example.enaula.repository.TabelaPrecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AulaService {

    private final AulaRepository aulaRepository;
    private final MateriaRepository materiaRepository;
    private final TabelaPrecoRepository tabelaPrecoRepository;
    private final AulaMapper aulaMapper;
    private final DisponibilidadeService disponibilidadeService;
    private final DailyService dailyService;

    // Novos serviços e repositórios para o envio dos e-mails.
    private final MatriculaRepository matriculaRepository;
    private final EmailService emailService;

    public AulaResponseDTO cadastrarAula(
            AulaRequestDTO dto,
            Professor professor
    ) {

        Materia materia =
                materiaRepository.findById(dto.materiaId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Matéria não encontrada"
                                )
                        );

        validarMateriaDoProfessor(materia, professor);

        validarDisponibilidade(dto, professor);

        validarFormatoEParticipantes(dto);

        validarValorMinimo(dto.valorAula(), professor);

        Aula aula =
                aulaMapper.toEntity(dto, materia, professor);

        return aulaMapper.toResponseDTO(
                aulaRepository.save(aula)
        );
    }

    public List<AulaResponseDTO> listarAulas(
            Professor professor
    ) {

        return aulaRepository
                .findAllByProfessor(professor)
                .stream()
                .map(aulaMapper::toResponseDTO)
                .toList();
    }

    public AulaResponseDTO buscarPorId(
            Long id,
            Professor professor
    ) {

        return aulaMapper.toResponseDTO(
                buscarEValidarPosse(id, professor)
        );
    }

    public AulaResponseDTO atualizarAula(
            Long id,
            AulaRequestDTO dto,
            Professor professor
    ) {

        Aula aula = buscarEValidarPosse(id, professor);

        Materia materia =
                materiaRepository.findById(dto.materiaId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Matéria não encontrada"
                                )
                        );

        validarMateriaDoProfessor(materia, professor);

        validarDisponibilidade(dto, professor);

        validarFormatoEParticipantes(dto);

        validarValorMinimo(dto.valorAula(), professor);

        aula.setMateria(materia);
        aula.setData(dto.data());
        aula.setHorario(dto.horario());
        aula.setDuracao(dto.duracao());
        aula.setModalidade(dto.modalidade());
        aula.setFormato(dto.formato());
        aula.setValorAula(dto.valorAula());
        aula.setQuantidadeParticipantes(
                dto.quantidadeParticipantes()
        );

        return aulaMapper.toResponseDTO(
                aulaRepository.save(aula)
        );
    }

    public void deletarAula(
            Long id,
            Professor professor
    ) {

        aulaRepository.delete(
                buscarEValidarPosse(id, professor)
        );
    }

    private void validarDisponibilidade(
            AulaRequestDTO dto,
            Professor professor
    ) {

        LocalTime horarioFim =
                dto.horario().plusHours(dto.duracao());

        if (horarioFim.equals(dto.horario())
                || horarioFim.isBefore(dto.horario())) {

            throw new IllegalArgumentException(
                    "A duração da aula ultrapassa o limite de um dia"
            );
        }

        boolean disponivel =
                disponibilidadeService.estaDisponivel(
                        professor,
                        dto.data(),
                        dto.horario(),
                        horarioFim
                );

        if (!disponivel) {

            throw new IllegalArgumentException(
                    "O horário informado não está dentro da disponibilidade do monitor"
            );
        }
    }

    private void validarFormatoEParticipantes(
            AulaRequestDTO dto
    ) {

        Integer participantes =
                dto.quantidadeParticipantes();

        if (participantes == null) {

            throw new IllegalArgumentException(
                    "A quantidade de participantes deve ser informada"
            );
        }

        if (dto.formato() == FormatoAula.INDIVIDUAL
                && participantes != 1) {

            throw new IllegalArgumentException(
                    "Aula individual deve ter exatamente 1 participante"
            );
        }

        if (dto.formato() == FormatoAula.GRUPO
                && participantes < 2) {

            throw new IllegalArgumentException(
                    "Aula em grupo deve ter pelo menos 2 participantes"
            );
        }
    }

    private void validarValorMinimo(
            BigDecimal valorAula,
            Professor professor
    ) {

        if (professor.getTitulacao() == null) {

            throw new IllegalArgumentException(
                    "O monitor precisa possuir uma titulação cadastrada para validar o valor mínimo da aula"
            );
        }

        TabelaPreco tabelaPreco =
                tabelaPrecoRepository
                        .findByTitulacao(professor.getTitulacao())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Não existe valor mínimo configurado para a titulação "
                                                + professor.getTitulacao()
                                )
                        );

        if (valorAula.compareTo(
                tabelaPreco.getValorMinimo()
        ) < 0) {

            throw new IllegalArgumentException(
                    "O valor da aula não pode ser inferior ao mínimo de R$ "
                            + tabelaPreco.getValorMinimo()
                            + " para a titulação "
                            + professor.getTitulacao()
            );
        }
    }

    private Aula buscarEValidarPosse(
            Long id,
            Professor professor
    ) {

        Aula aula =
                aulaRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Aula não encontrada"
                                )
                        );

        if (aula.getProfessor() == null
                || !aula.getProfessor()
                .getId()
                .equals(professor.getId())) {

            throw new AccessDeniedException(
                    "Você não tem permissão para acessar essa aula"
            );
        }

        return aula;
    }

    private void validarMateriaDoProfessor(
            Materia materia,
            Professor professor
    ) {

        if (materia.getProfessor() == null
                || !materia.getProfessor()
                .getId()
                .equals(professor.getId())) {

            throw new AccessDeniedException(
                    "Você não tem permissão para utilizar essa matéria"
            );
        }
    }

    // Inicia a aula e envia o link aos alunos com matrícula aceita.
    public AulaResponseDTO iniciarAula(
            Long id,
            Professor professor
    ) {

        Aula aula = buscarEValidarPosse(id, professor);

        if (aula.getModalidade() != Modalidade.ONLINE) {

            throw new IllegalArgumentException(
                    "Só é possível iniciar sala de vídeo para aulas com modalidade ONLINE"
            );
        }

        // Se a sala já existe, não cria outra nem reenvia os e-mails.
        if (aula.getSalaUrl() != null
                && !aula.getSalaUrl().isBlank()) {

            return aulaMapper.toResponseDTO(aula);
        }

        // Cria a sala virtual no Daily.
        DailyRoomResponseDTO sala =
                dailyService.criarSalaComGravacao();

        aula.setSalaUrl(sala.url());
        aula.setSalaNome(sala.name());

        // Salva a sala antes de enviar as notificações.
        Aula aulaSalva = aulaRepository.save(aula);

        // Busca somente os alunos com matrícula aceita nesta aula.
        List<Matricula> matriculasAceitas =
                matriculaRepository
                        .findAllByAulaAndStatusOrderByDataSolicitacaoAsc(
                                aulaSalva,
                                StatusMatricula.ACEITA
                        );

        // Envia o link individualmente para cada aluno aprovado.
        for (Matricula matricula : matriculasAceitas) {

            Aluno aluno = matricula.getAluno();

            String assunto =
                    "Aula iniciada - " + aulaSalva.getMateria().getNome();

            String mensagem =
                    "Olá, " + aluno.getNome() + "!\n\n"
                            + "Sua aula de "
                            + aulaSalva.getMateria().getNome()
                            + " foi iniciada.\n\n"
                            + "Acesse a sala virtual pelo link abaixo:\n"
                            + aulaSalva.getSalaUrl()
                            + "\n\n"
                            + "Até a aula!\n"
                            + "Equipe EnAula";

            emailService.enviarEmail(
                    aluno.getEmail(),
                    assunto,
                    mensagem
            );
        }

        return aulaMapper.toResponseDTO(aulaSalva);
    }
}
