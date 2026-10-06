package com.example.enaula.repository;

import com.example.enaula.entity.TabelaPreco;
import com.example.enaula.entity.TitulacaoMonitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TabelaPrecoRepository
        extends JpaRepository<TabelaPreco, Long> {

    Optional<TabelaPreco> findByTitulacao(
            TitulacaoMonitor titulacao
    );
}
