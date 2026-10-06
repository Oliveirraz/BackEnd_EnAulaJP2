package com.example.enaula.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(
        name = "tabela_preco",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_tabela_preco_titulacao",
                columnNames = "titulacao"
        )
)
@Getter
@Setter
public class TabelaPreco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private TitulacaoMonitor titulacao;

    @Column(
            name = "valor_minimo",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal valorMinimo;

