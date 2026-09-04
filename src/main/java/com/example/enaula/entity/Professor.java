package com.example.enaula.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "professor")
@Getter
@Setter
public class Professor extends Usuario {

    @Column(nullable = false)
    private String perfil;

    @Column(
            name = "valor_hora_aula",
            precision = 10,
            scale = 2
    )
    private BigDecimal valorHoraAula;


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}