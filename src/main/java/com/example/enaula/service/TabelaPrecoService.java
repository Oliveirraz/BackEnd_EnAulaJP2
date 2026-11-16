package com.example.enaula.service;

import com.example.enaula.dto.TabelaPrecoResponseDTO;
import com.example.enaula.entity.TabelaPreco;
import com.example.enaula.entity.TitulacaoProfessor;
import com.example.enaula.repository.TabelaPrecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

// Regras da tabela de preços: o valor da hora-aula do professor nunca pode ficar
// abaixo do mínimo da titulação dele.
@Service
@RequiredArgsConstructor
public class TabelaPrecoService {

    private static final Map<TitulacaoProfessor, String> ROTULOS = Map.of(
            TitulacaoProfessor.GRADUACAO, "Graduação",
            TitulacaoProfessor.ESPECIALIZACAO, "Especialização",
            TitulacaoProfessor.MESTRADO, "Mestrado",
            TitulacaoProfessor.DOUTORADO, "Doutorado"
    );

    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    private final TabelaPrecoRepository tabelaPrecoRepository;

    // Lista os valores mínimos de todas as titulações (usada pela tela de cadastro).
    public List<TabelaPrecoResponseDTO> listar() {
        return tabelaPrecoRepository.findAll()
                .stream()
                .map(preco -> new TabelaPrecoResponseDTO(
                        preco.getTitulacao(),
                        preco.getValorMinimo()
                ))
                .toList();
    }

    // Define o valor final da hora-aula:
    // - sem valor informado, vale o mínimo da titulação;
    // - abaixo do mínimo, é recusado;
    // - igual ou acima do mínimo, é mantido.
    public BigDecimal definirValorHoraAula(
            TitulacaoProfessor titulacao,
            BigDecimal valorInformado
    ) {

        BigDecimal minimo = buscarValorMinimo(titulacao);

        if (valorInformado == null) {
            return minimo;
        }

        if (valorInformado.compareTo(minimo) < 0) {
            throw new IllegalArgumentException(
                    "O valor mínimo da hora-aula para "
                            + ROTULOS.get(titulacao)
                            + " é "
                            + NumberFormat.getCurrencyInstance(PT_BR).format(minimo)
                            + "."
            );
        }

        return valorInformado;
    }

    private BigDecimal buscarValorMinimo(TitulacaoProfessor titulacao) {
        return tabelaPrecoRepository.findByTitulacao(titulacao)
                .map(TabelaPreco::getValorMinimo)
                .orElseThrow(() -> new IllegalStateException(
                        "A tabela de preços não tem valor para a titulação "
                                + titulacao + "."
                ));
    }
}