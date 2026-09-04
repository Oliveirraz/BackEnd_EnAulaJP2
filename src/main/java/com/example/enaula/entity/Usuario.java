package com.example.enaula.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "nome")
    private String nome;

    @Column(nullable = false, name = "email")
    private String email;

    @Column(nullable = false, name = "senha")
    private String senha;


    @Column(name = "foto")
    private String foto;


    public abstract Collection<? extends GrantedAuthority> getAuthorities();
}