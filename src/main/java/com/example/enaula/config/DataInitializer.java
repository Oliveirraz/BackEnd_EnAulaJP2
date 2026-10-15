package com.example.enaula.config;

import com.example.enaula.entity.TabelaPreco;
import com.example.enaula.entity.TitulacaoProfessor;
import com.example.enaula.entity.TitulacaoProfessor;
import com.example.enaula.repository.TabelaPrecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final TabelaPrecoRepository tabelaPrecoRepository;

    @Bean
    CommandLineRunner inicializarTabelaPrecos() {
        return args -> {

            criarPrecoSeNaoExistir(
                    TitulacaoProfessor.GRADUACAO,
                    new BigDecimal("30.00")
            );

            criarPrecoSeNaoExistir(
                    TitulacaoProfessor.ESPECIALIZACAO,
                    new BigDecimal("40.00")
            );

            criarPrecoSeNaoExistir(
                    TitulacaoProfessor.MESTRADO,
                    new BigDecimal("50.00")
            );

            criarPrecoSeNaoExistir(
                    TitulacaoProfessor.DOUTORADO,
                    new BigDecimal("70.00")
            );
        };
    }

    private void criarPrecoSeNaoExistir(
            TitulacaoProfessor titulacao,
            BigDecimal valorMinimo
    ) {

        if (tabelaPrecoRepository
                .findByTitulacao(titulacao)
                .isEmpty()) {

            TabelaPreco tabelaPreco = new TabelaPreco();

            tabelaPreco.setTitulacao(titulacao);
            tabelaPreco.setValorMinimo(valorMinimo);

            tabelaPrecoRepository.save(tabelaPreco);
        }
    }
}