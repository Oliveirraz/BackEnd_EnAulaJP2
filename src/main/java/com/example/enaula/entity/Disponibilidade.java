package com.example.enaula.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "disponibilidade")
@Getter
@Setter
public class Disponibilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDisponibilidade tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana")
    private DiaSemana diaSemana;

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "horario_inicio", nullable = false)
    private LocalTime horarioInicio;

    @Column(name = "horario_fim", nullable = false)
    private LocalTime horarioFim;
}