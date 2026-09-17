package com.example.enaula.repository;

import com.example.enaula.entity.Materia;
import com.example.enaula.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MateriaRepository extends JpaRepository<Materia, Long> {

    List<Materia> findAllByProfessor(Professor professor);
}
