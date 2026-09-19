package com.example.enaula.repository;

import com.example.enaula.entity.Aula;
import com.example.enaula.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AulaRepository extends JpaRepository<Aula, Long> {

    List<Aula> findAllByProfessor(Professor professor);
}