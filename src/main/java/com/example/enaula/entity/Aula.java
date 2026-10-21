package com.example.enaula.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "aula")
@Getter
@Setter
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "materia_id", nullable = false)
    private Materia materia;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime horario;

    @Column(nullable = false)
    private Integer duracao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Modalidade modalidade;

    @Enumerated(EnumType.STRING)
    @Column
    private FormatoAula formato;

    @Column(name= "sala_url")
    private String salaUrl;

    @Column(name = "sala_nome")
    private String salaNome;

    @Column(name = "gravacao_chave_objeto")
    private String gravacaoChaveObjeto;

    @Column(name = "gravacao_nome_original")
    private String gravacaoNomeOriginal;

    @Column(name = "gravacao_data_envio")
    private LocalDateTime gravacaoDataEnvio;

    @Column(
            name = "valor_aula",
            precision = 10,
            scale = 2
    )
    private BigDecimal valorAula;

    @Column(name = "quantidade_participantes")
    private Integer quantidadeParticipantes;
}