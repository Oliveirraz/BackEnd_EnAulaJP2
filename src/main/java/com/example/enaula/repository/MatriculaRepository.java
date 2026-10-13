package com.example.enaula.repository;

import com.example.enaula.entity.Aluno;
import com.example.enaula.entity.Aula;
import com.example.enaula.entity.Matricula;
import com.example.enaula.entity.StatusMatricula;
import org.springframework.data.jpa.repository.JpaRepository;

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

    Optional<Matricula> findByIdAndAluno(Long id, Aluno aluno);
}