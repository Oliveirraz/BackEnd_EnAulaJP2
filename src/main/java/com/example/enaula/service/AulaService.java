package com.example.enaula.service;

import com.example.enaula.dto.AulaRequestDTO;
import com.example.enaula.dto.AulaResponseDTO;
import com.example.enaula.entity.Aula;
import com.example.enaula.entity.Materia;
import com.example.enaula.entity.Professor;
import com.example.enaula.exception.ResourceNotFoundException;
import com.example.enaula.mapper.AulaMapper;
import com.example.enaula.repository.AulaRepository;
import com.example.enaula.repository.MateriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AulaService {

    private final AulaRepository aulaRepository;

    private final MateriaRepository materiaRepository;

    private final AulaMapper aulaMapper;

    private final DisponibilidadeService disponibilidadeService;


    // ============================================================
    // CADASTRAR AULA
    // ============================================================

    public AulaResponseDTO cadastrarAula(
            AulaRequestDTO dto,
            Professor professor
    ) {

        Materia materia =
                materiaRepository
                        .findById(dto.materiaId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Matéria não encontrada"
                                )
                        );

        validarMateriaDoProfessor(
                materia,
                professor
        );

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

        Aula aulaSalva =
                aulaRepository.save(aula);

        return aulaMapper.toResponseDTO(
                aulaSalva
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

        Aula aula =
                buscarEValidarPosse(
                        id,
                        professor
                );

        return aulaMapper.toResponseDTO(aula);
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
                materiaRepository
                        .findById(dto.materiaId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Matéria não encontrada"
                                )
                        );

        validarMateriaDoProfessor(
                materia,
                professor
        );

        validarDisponibilidade(
                dto,
                professor
        );

        aula.setMateria(materia);

        aula.setData(
                dto.data()
        );

        aula.setHorario(
                dto.horario()
        );

        aula.setDuracao(
                dto.duracao()
        );

        aula.setModalidade(
                dto.modalidade()
        );

        Aula aulaAtualizada =
                aulaRepository.save(aula);

        return aulaMapper.toResponseDTO(
                aulaAtualizada
        );
    }


    // ============================================================
    // DELETAR AULA
    // ============================================================

    public void deletarAula(
            Long id,
            Professor professor
    ) {

        Aula aula =
                buscarEValidarPosse(
                        id,
                        professor
                );

        aulaRepository.delete(aula);
    }


    // ============================================================
    // VALIDAR DISPONIBILIDADE
    // ============================================================

    private void validarDisponibilidade(
            AulaRequestDTO dto,
            Professor professor
    ) {

        LocalTime horarioFim =
                dto.horario()
                        .plusHours(dto.duracao());

        /*
         * Caso a soma da duração ultrapasse 00:00,
         * significa que a aula passou para o dia seguinte.
         *
         * A aula não pode atravessar dias.
         */
        if (
                horarioFim.equals(dto.horario())
                        || horarioFim.isBefore(dto.horario())
        ) {

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
                    "O horário informado não está dentro " +
                            "da disponibilidade do monitor"
            );
        }
    }


    // ============================================================
    // VALIDAR POSSE
    // ============================================================

    private Aula buscarEValidarPosse(
            Long id,
            Professor professor
    ) {

        Aula aula =
                aulaRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Aula não encontrada"
                                )
                        );

        if (
                aula.getProfessor() == null
                        || !aula.getProfessor()
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

        if (
                materia.getProfessor() == null
                        || !materia.getProfessor()
                        .getId()
                        .equals(professor.getId())
        ) {

            throw new AccessDeniedException(
                    "Você não tem permissão para utilizar essa matéria"
            );
        }
    }
}