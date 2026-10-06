package com.example.enaula.service;

import com.example.enaula.dto.DisponibilidadeRequestDTO;
import com.example.enaula.dto.DisponibilidadeResponseDTO;
import com.example.enaula.entity.DiaSemana;
import com.example.enaula.entity.Disponibilidade;
import com.example.enaula.entity.Professor;
import com.example.enaula.entity.TipoDisponibilidade;
import com.example.enaula.exception.ResourceNotFoundException;
import com.example.enaula.repository.DisponibilidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DisponibilidadeService {

    private final DisponibilidadeRepository disponibilidadeRepository;

    public DisponibilidadeResponseDTO cadastrar(
            DisponibilidadeRequestDTO dto,
            Professor professor
    ) {
        validarDados(dto);

        Disponibilidade disponibilidade = new Disponibilidade();

        disponibilidade.setProfessor(professor);

        preencher(disponibilidade, dto);

        validarConflito(
                disponibilidade,
                professor,
                null
        );

        return toResponse(
                disponibilidadeRepository.save(disponibilidade)
        );
    }

    public List<DisponibilidadeResponseDTO> listar(
            Professor professor
    ) {
        return disponibilidadeRepository
                .findAllByProfessorOrderByHorarioInicio(professor)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public DisponibilidadeResponseDTO buscarPorId(
            Long id,
            Professor professor
    ) {
        return toResponse(
                buscarEValidarPosse(id, professor)
        );
    }

    public DisponibilidadeResponseDTO atualizar(
            Long id,
            DisponibilidadeRequestDTO dto,
            Professor professor
    ) {
        validarDados(dto);

        Disponibilidade disponibilidade =
                buscarEValidarPosse(id, professor);

        preencher(disponibilidade, dto);

        validarConflito(
                disponibilidade,
                professor,
                id
        );

        return toResponse(
                disponibilidadeRepository.save(disponibilidade)
        );
    }

    public void deletar(
            Long id,
            Professor professor
    ) {
        disponibilidadeRepository.delete(
                buscarEValidarPosse(id, professor)
        );
    }

    public boolean estaDisponivel(
            Professor professor,
            LocalDate data,
            LocalTime inicio,
            LocalTime fim
    ) {

        if (
                data == null
                        || inicio == null
                        || fim == null
                        || !inicio.isBefore(fim)
        ) {
            return false;
        }

        DiaSemana diaSemana =
                converterDiaSemana(data.getDayOfWeek());

        boolean semanal =
                disponibilidadeRepository
                        .existsByProfessorAndTipoAndDiaSemanaAndHorarioInicioLessThanAndHorarioFimGreaterThan(
                                professor,
                                TipoDisponibilidade.SEMANAL,
                                diaSemana,
                                fim,
                                inicio
                        );

        if (semanal) {
            return true;
        }

        return disponibilidadeRepository
                .existsByProfessorAndTipoAndDataAndHorarioInicioLessThanAndHorarioFimGreaterThan(
                        professor,
                        TipoDisponibilidade.MENSAL,
                        data,
                        fim,
                        inicio
                );
    }

    private void validarDados(
            DisponibilidadeRequestDTO dto
    ) {

        if (dto.tipo() == TipoDisponibilidade.SEMANAL) {

            if (dto.diaSemana() == null) {
                throw new IllegalArgumentException(
                        "O dia da semana é obrigatório para disponibilidade semanal"
                );
            }

            if (dto.data() != null) {
                throw new IllegalArgumentException(
                        "A disponibilidade semanal não deve informar uma data específica"
                );
            }
        }

        if (dto.tipo() == TipoDisponibilidade.MENSAL) {

            if (dto.data() == null) {
                throw new IllegalArgumentException(
                        "A data é obrigatória para disponibilidade mensal"
                );
            }

            if (dto.diaSemana() != null) {
                throw new IllegalArgumentException(
                        "A disponibilidade mensal não deve informar dia da semana"
                );
            }
        }

        if (!dto.horarioInicio().isBefore(dto.horarioFim())) {
            throw new IllegalArgumentException(
                    "O horário inicial deve ser anterior ao horário final"
            );
        }
    }

    private void preencher(
            Disponibilidade disponibilidade,
            DisponibilidadeRequestDTO dto
    ) {

        disponibilidade.setTipo(dto.tipo());

        disponibilidade.setDiaSemana(
                dto.diaSemana()
        );

        disponibilidade.setData(
                dto.data()
        );

        disponibilidade.setHorarioInicio(
                dto.horarioInicio()
        );

        disponibilidade.setHorarioFim(
                dto.horarioFim()
        );
    }

    private void validarConflito(
            Disponibilidade disponibilidade,
            Professor professor,
            Long idIgnorado
    ) {

        List<Disponibilidade> existentes =
                disponibilidadeRepository
                        .findAllByProfessorOrderByHorarioInicio(
                                professor
                        );

        boolean conflito =
                existentes.stream()

                        .filter(item ->
                                idIgnorado == null
                                        || !item.getId().equals(idIgnorado)
                        )

                        .filter(item ->
                                item.getTipo()
                                        == disponibilidade.getTipo()
                        )

                        .filter(item ->
                                disponibilidade.getTipo()
                                        == TipoDisponibilidade.SEMANAL

                                        ? item.getDiaSemana()
                                        == disponibilidade.getDiaSemana()

                                        : item.getData()
                                        .equals(disponibilidade.getData())
                        )

                        .anyMatch(item ->
                                horariosSeSobrepoem(
                                        item.getHorarioInicio(),
                                        item.getHorarioFim(),
                                        disponibilidade.getHorarioInicio(),
                                        disponibilidade.getHorarioFim()
                                )
                        );

        if (conflito) {
            throw new IllegalArgumentException(
                    "Já existe uma disponibilidade cadastrada nesse período"
            );
        }
    }

    private boolean horariosSeSobrepoem(
            LocalTime inicio1,
            LocalTime fim1,
            LocalTime inicio2,
            LocalTime fim2
    ) {

        return inicio1.isBefore(fim2)
                && inicio2.isBefore(fim1);
    }

    private Disponibilidade buscarEValidarPosse(
            Long id,
            Professor professor
    ) {

        Disponibilidade disponibilidade =
                disponibilidadeRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Disponibilidade não encontrada"
                                )
                        );

        if (
                !disponibilidade
                        .getProfessor()
                        .getId()
                        .equals(professor.getId())
        ) {

            throw new AccessDeniedException(
                    "Você não tem permissão para acessar essa disponibilidade"
            );
        }

        return disponibilidade;
    }

    private DisponibilidadeResponseDTO toResponse(
            Disponibilidade disponibilidade
    ) {

        return new DisponibilidadeResponseDTO(
                disponibilidade.getId(),
                disponibilidade.getProfessor().getId(),
                disponibilidade.getTipo(),
                disponibilidade.getDiaSemana(),
                disponibilidade.getData(),
                disponibilidade.getHorarioInicio(),
                disponibilidade.getHorarioFim()
        );
    }

    private DiaSemana converterDiaSemana(
            DayOfWeek dayOfWeek
    ) {

        return switch (dayOfWeek) {

            case MONDAY ->
                    DiaSemana.SEGUNDA;

            case TUESDAY ->
                    DiaSemana.TERCA;

            case WEDNESDAY ->
                    DiaSemana.QUARTA;

            case THURSDAY ->
                    DiaSemana.QUINTA;

            case FRIDAY ->
                    DiaSemana.SEXTA;

            case SATURDAY ->
                    DiaSemana.SABADO;

            case SUNDAY ->
                    DiaSemana.DOMINGO;
        };
    }
}