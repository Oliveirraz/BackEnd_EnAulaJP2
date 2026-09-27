package com.example.enaula.repository;

import com.example.enaula.entity.MaterialApoio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialApoioRepository extends JpaRepository<MaterialApoio, Long> {

    List<MaterialApoio> findByAulaId(Long aulaId);

    List<MaterialApoio> findByProfessorId(Long professorId);
}
