package com.example.enaula.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "professor")
@Getter
@Setter
public class Professor extends Usuario {


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}