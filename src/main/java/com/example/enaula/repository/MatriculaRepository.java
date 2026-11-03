package com.example.enaula.repository;

import com.example.enaula.entity.Aluno;
import com.example.enaula.entity.Aula;
import com.example.enaula.entity.Matricula;
import com.example.enaula.entity.StatusMatricula;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MatriculaRepository
        extends JpaRepository<Matricula, Long> {

    boolean existsByAulaAndAluno(Aula aula, Aluno aluno);

    List<Matricula>
    findAllByAulaAndStatusOrderByDataSolicitacaoAsc(
            Aula aula,
            StatusMatricula status
    );

    List<Matricula>
    findAllByAlunoOrderByDataSolicitacaoDesc(Aluno aluno);

    Optional<Matricula> findByIdAndAluno(
            Long id,
            Aluno aluno
    );

    // Consulta as matrículas aceitas e canceladas do aluno.
    // As aulas são ordenadas da mais recente para a mais antiga.
    @Query("""
            SELECT m FROM Matricula m
            JOIN FETCH m.aula a
            JOIN FETCH a.materia materia
            JOIN FETCH a.professor professor
            WHERE m.aluno = :aluno
            AND m.status IN (
                com.example.enaula.entity.StatusMatricula.ACEITA,
                com.example.enaula.entity.StatusMatricula.CANCELADA
            )
            ORDER BY a.data DESC, a.horario DESC
            """)
    List<Matricula> buscarHistoricoDoAluno(
            @Param("aluno") Aluno aluno
    );
}