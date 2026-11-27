
package com.example.enaula.repository;

import com.example.enaula.entity.MaterialApoio;
import com.example.enaula.entity.Aluno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialApoioRepository
        extends JpaRepository<MaterialApoio, Long> {

    List<MaterialApoio> findByAulaId(Long aulaId);

    List<MaterialApoio> findByProfessorId(Long professorId);

    @Query("""
        SELECT DISTINCT m FROM MaterialApoio m
        JOIN FETCH m.aula a
        JOIN FETCH a.materia
        JOIN FETCH m.professor
        WHERE a.id IN (
            SELECT mat.aula.id FROM Matricula mat
            WHERE mat.aluno = :aluno
            AND mat.status =
                com.example.enaula.entity.StatusMatricula.ACEITA
        )
        ORDER BY m.dataEnvio DESC
        """)
    List<MaterialApoio> listarMateriaisDoAluno(
            @Param("aluno") Aluno aluno
    );
}
