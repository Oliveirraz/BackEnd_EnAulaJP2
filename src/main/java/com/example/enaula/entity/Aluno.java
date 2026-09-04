package com.example.enaula.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Aluno")
@Getter
@Setter
public class Aluno extends Usuario{

}
