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
import java.util.List;

@Service
@RequiredArgsConstructor
public class AulaService {

    private final AulaRepository aulaRepository;
    private final MateriaRepository materiaRepository;
    private final TabelaPrecoRepository tabelaPrecoRepository;
    private final AulaMapper aulaMapper;

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

        validarFormatoEParticipantes(dto);

        validarValorMinimo(
                dto.valorAula(),
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
                buscarEValidarPosse(
                        id,
                        professor
                )
        );
    }

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

        validarFormatoEParticipantes(dto);

        validarValorMinimo(
                dto.valorAula(),
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

    private void validarFormatoEParticipantes(
            AulaRequestDTO dto
    ) {

        Integer participantes =
                dto.quantidadeParticipantes();

        if (
                dto.formato() == FormatoAula.INDIVIDUAL
                        && participantes != null
                        && participantes != 1
        ) {

            throw new IllegalArgumentException(
                    "Aula individual deve ter exatamente 1 participante"
            );
        }

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

        if (participantes == null) {

            throw new IllegalArgumentException(
                    "A quantidade de participantes é obrigatória"
            );
        }
    }

    private void validarValorMinimo(
            BigDecimal valorAula,
            Professor professor
    ) {

        if (professor.getTitulacao() == null) {

            throw new IllegalArgumentException(
                    "O monitor precisa possuir uma titulação cadastrada " +
                            "para validar o valor mínimo da aula"
            );
        }

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

    private void validarMateriaDoProfessor(
            Materia materia,
            Professor professor
    ) {

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