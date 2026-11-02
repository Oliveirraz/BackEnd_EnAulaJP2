
package com.example.enaula.repository;

import com.example.enaula.entity.Aula;
import com.example.enaula.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AulaRepository extends JpaRepository<Aula, Long> {

    List<Aula> findAllByProfessor(Professor professor);

    List<Aula> findAllByProfessorAndData(
            Professor professor,
            LocalDate data
    );

    @Query("""
            SELECT a
            FROM Aula a
            JOIN FETCH a.professor p
            JOIN FETCH a.materia m
            WHERE a.data >= :data
            """)
    List<Aula> buscarAulasFuturas(
            @Param("data") LocalDate data
    );
}