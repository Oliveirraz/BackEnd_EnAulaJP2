package com.example.enaula.repository;

import com.example.enaula.entity.DiaSemana;
import com.example.enaula.entity.Disponibilidade;
import com.example.enaula.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DisponibilidadeRepository
        extends JpaRepository<Disponibilidade, Long> {

    List<Disponibilidade> findAllByProfessorOrderByHorarioInicio(
            Professor professor
    );

    boolean existsByProfessorAndTipoAndDiaSemanaAndHorarioInicioLessThanAndHorarioFimGreaterThan(
            Professor professor,
            com.example.enaula.entity.TipoDisponibilidade tipo,
            DiaSemana diaSemana,
            java.time.LocalTime horarioFim,
            java.time.LocalTime horarioInicio
    );

    boolean existsByProfessorAndTipoAndDataAndHorarioInicioLessThanAndHorarioFimGreaterThan(
            Professor professor,
            com.example.enaula.entity.TipoDisponibilidade tipo,
            LocalDate data,
            java.time.LocalTime horarioFim,
            java.time.LocalTime horarioInicio
    );
}