package com.example.enaula.service;

import com.example.enaula.dto.AulaRequestDTO;
import com.example.enaula.dto.AulaResponseDTO;
import com.example.enaula.entity.Aula;
import com.example.enaula.entity.FormatoAula;
import com.example.enaula.entity.Materia;
import com.example.enaula.entity.Professor;
import com.example.enaula.entity.TabelaPreco;
import com.example.enaula.mapper.AulaMapper;
import com.example.enaula.repository.AulaRepository;
import com.example.enaula.repository.MateriaRepository;
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

    /*
     * Serviço responsável pela validação da disponibilidade
     * do professor antes da criação ou alteração de uma aula.
     */
    private final DisponibilidadeService disponibilidadeService;


    // ============================================================
    // CADASTRAR AULA
    // ============================================================

    public AulaResponseDTO cadastrarAula(
            AulaRequestDTO dto,
            Professor professor
    ) {

        Materia materia =
                materiaRepository.findById(dto.materiaId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Matéria não encontrada"
                                )
                        );

        validarMateriaDoProfessor(
                materia,
                professor
        );

        // Valida formato e quantidade de participantes
        validarFormatoEParticipantes(dto);

        // Valida o valor mínimo conforme a titulação
        // do professor
        validarValorMinimo(
                dto.valorAula(),
                professor
        );

        // Valida se o horário escolhido está dentro
        // da disponibilidade cadastrada pelo professor
        validarDisponibilidade(
                dto,
                professor
        );

        Aula aula =
                aulaMapper.toEntity(
                        dto,
                        materia,
                        professor
                );

        return aulaMapper.toResponseDTO(
                aulaRepository.save(aula)
        );
    }


    // ============================================================
    // LISTAR AULAS
    // ============================================================

    public List<AulaResponseDTO> listarAulas(
            Professor professor
    ) {

        return aulaRepository
                .findAllByProfessor(professor)
                .stream()
                .map(aulaMapper::toResponseDTO)
                .toList();
    }


    // ============================================================
    // BUSCAR AULA POR ID
    // ============================================================

    public AulaResponseDTO buscarPorId(
            Long id,
            Professor professor
    ) {

        return aulaMapper.toResponseDTO(
                buscarEValidarPosse(
                        id,
                        professor
                )
        );
    }


    // ============================================================
    // ATUALIZAR AULA
    // ============================================================

    public AulaResponseDTO atualizarAula(
            Long id,
            AulaRequestDTO dto,
            Professor professor
    ) {

        Aula aula =
                buscarEValidarPosse(
                        id,
                        professor
                );

        Materia materia =
                materiaRepository.findById(dto.materiaId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Matéria não encontrada"
                                )
                        );

        validarMateriaDoProfessor(
                materia,
                professor
        );

        // Valida formato e quantidade de participantes
        validarFormatoEParticipantes(dto);

        // Valida valor mínimo conforme a titulação
        validarValorMinimo(
                dto.valorAula(),
                professor
        );

        // Valida se o novo horário está dentro
        // da disponibilidade do professor
        validarDisponibilidade(
                dto,
                professor
        );

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


    // ============================================================
    // DELETAR AULA
    // ============================================================

    public void deletarAula(
            Long id,
            Professor professor
    ) {

        aulaRepository.delete(
                buscarEValidarPosse(
                        id,
                        professor
                )
        );
    }


    // ============================================================
    // VALIDAR FORMATO E PARTICIPANTES
    // ============================================================

    private void validarFormatoEParticipantes(
            AulaRequestDTO dto
    ) {

        Integer participantes =
                dto.quantidadeParticipantes();

        /*
         * Aula individual precisa possuir exatamente
         * 1 participante.
         */
        if (
                dto.formato() == FormatoAula.INDIVIDUAL
                        && participantes != null
                        && participantes != 1
        ) {

            throw new IllegalArgumentException(
                    "Aula individual deve ter exatamente 1 participante"
            );
        }


        /*
         * Aula em grupo precisa possuir pelo menos
         * 2 participantes.
         */
        if (
                dto.formato() == FormatoAula.GRUPO
                        && (
                        participantes == null
                                || participantes < 2
                )
        ) {

            throw new IllegalArgumentException(
                    "Aula em grupo deve ter pelo menos 2 participantes"
            );
        }


        /*
         * A quantidade de participantes é obrigatória
         * independentemente do formato.
         */
        if (participantes == null) {

            throw new IllegalArgumentException(
                    "A quantidade de participantes é obrigatória"
            );
        }
    }


    // ============================================================
    // VALIDAR VALOR MÍNIMO
    // ============================================================

    private void validarValorMinimo(
            BigDecimal valorAula,
            Professor professor
    ) {

        /*
         * O professor precisa possuir uma titulação
         * para que o sistema consiga consultar a tabela
         * de valores mínimos.
         */
        if (professor.getTitulacao() == null) {

            throw new IllegalArgumentException(
                    "O monitor precisa possuir uma titulação cadastrada " +
                            "para validar o valor mínimo da aula"
            );
        }


        /*
         * Busca o valor mínimo correspondente à titulação
         * do professor.
         */
        TabelaPreco tabelaPreco =
                tabelaPrecoRepository
                        .findByTitulacao(
                                professor.getTitulacao()
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Não existe valor mínimo configurado " +
                                                "para a titulação "
                                                + professor.getTitulacao()
                                )
                        );


        /*
         * Impede que o professor cadastre uma aula
         * abaixo do valor mínimo definido para sua titulação.
         */
        if (
                valorAula.compareTo(
                        tabelaPreco.getValorMinimo()
                ) < 0
        ) {

            throw new IllegalArgumentException(
                    "O valor da aula não pode ser inferior " +
                            "ao mínimo de R$ "
                            + tabelaPreco.getValorMinimo()
                            + " para a titulação "
                            + professor.getTitulacao()
            );
        }
    }


    // ============================================================
    // VALIDAR DISPONIBILIDADE
    // ============================================================

    private void validarDisponibilidade(
            AulaRequestDTO dto,
            Professor professor
    ) {

        /*
         * Calcula o horário de término da aula.
         *
         * Exemplo:
         *
         * horário = 14:00
         * duração = 2 horas
         *
         * término = 16:00
         */
        LocalTime horarioFim =
                dto.horario().plusHours(dto.duracao());


        /*
         * Impede que uma aula ultrapasse o limite
         * de um dia.
         */
        if (
                horarioFim.isBefore(dto.horario())
                        || horarioFim.equals(dto.horario())
        ) {

            throw new IllegalArgumentException(
                    "A duração da aula ultrapassa o limite de um dia"
            );
        }


        /*
         * Verifica se o período completo da aula está
         * dentro de alguma disponibilidade cadastrada
         * pelo professor.
         *
         * A disponibilidade pode ser:
         *
         * - semanal
         * - mensal
         */
        if (
                !disponibilidadeService.estaDisponivel(
                        professor,
                        dto.data(),
                        dto.horario(),
                        horarioFim
                )
        ) {

            throw new IllegalArgumentException(
                    "O horário informado não está dentro da " +
                            "disponibilidade do monitor"
            );
        }
    }


    // ============================================================
    // VALIDAR POSSE DA AULA
    // ============================================================

    private Aula buscarEValidarPosse(
            Long id,
            Professor professor
    ) {

        Aula aula =
                aulaRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Aula não encontrada"
                                )
                        );


        /*
         * Garante que o professor só consiga acessar,
         * alterar ou excluir suas próprias aulas.
         */
        if (
                !aula.getProfessor()
                        .getId()
                        .equals(professor.getId())
        ) {

            throw new AccessDeniedException(
                    "Você não tem permissão para acessar essa aula"
            );
        }

        return aula;
    }


    // ============================================================
    // VALIDAR MATÉRIA DO PROFESSOR
    // ============================================================

    private void validarMateriaDoProfessor(
            Materia materia,
            Professor professor
    ) {

        /*
         * A matéria utilizada na aula precisa pertencer
         * ao professor que está criando a aula.
         */
        if (
                !materia.getProfessor()
                        .getId()
                        .equals(professor.getId())
        ) {

            throw new AccessDeniedException(
                    "Você não tem permissão para utilizar essa matéria"
            );
        }
    }
}