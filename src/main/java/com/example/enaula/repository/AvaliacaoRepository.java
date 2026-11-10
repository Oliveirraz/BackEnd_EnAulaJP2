package com.example.enaula.repository;

import com.example.enaula.entity.Avaliacao;
import com.example.enaula.entity.Aluno;
import com.example.enaula.entity.Aula;
import com.example.enaula.entity.Professor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AvaliacaoRepository
        extends JpaRepository<Avaliacao, Long> {

    // Verifica se o aluno já avaliou essa aula.
    boolean existsByAulaAndAluno(
            Aula aula,
            Aluno aluno
    );

    // Busca a avaliação feita pelo aluno em determinada aula.
    Optional<Avaliacao> findByAulaAndAluno(
            Aula aula,
            Aluno aluno
    );

    // Lista avaliações recebidas pelo professor.
    List<Avaliacao> findAllByProfessorOrderByDataAvaliacaoDesc(
            Professor professor
    );

    // Calcula a média das avaliações do professor.
    @org.springframework.data.jpa.repository.Query("""
            SELECT AVG(a.nota)
            FROM Avaliacao a
            WHERE a.professor = :professor
            """)
    Double calcularMediaProfessor(
            @org.springframework.data.repository.query.Param("professor")
            Professor professor
    );

    // Conta quantas avaliações o professor recebeu.
    long countByProfessor(Professor professor);
}