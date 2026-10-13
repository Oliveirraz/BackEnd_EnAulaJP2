package com.example.enaula.repository;

import com.example.enaula.entity.Disponibilidade;
import com.example.enaula.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisponibilidadeRepository
        extends JpaRepository<Disponibilidade, Long> {

    List<Disponibilidade> findAllByProfessorOrderByHorarioInicio(
            Professor professor
    );
}