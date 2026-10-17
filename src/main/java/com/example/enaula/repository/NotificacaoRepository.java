package com.example.enaula.repository;

import com.example.enaula.entity.Aluno;
import com.example.enaula.entity.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacaoRepository
        extends JpaRepository<Notificacao, Long> {

    List<Notificacao>
    findAllByAlunoOrderByDataCriacaoDesc(Aluno aluno);
}
