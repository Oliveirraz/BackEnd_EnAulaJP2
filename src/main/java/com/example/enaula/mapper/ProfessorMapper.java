package com.example.enaula.mapper;

import com.example.enaula.dto.ProfessorRequestDTO;
import com.example.enaula.dto.ProfessorResponseDTO;
import com.example.enaula.entity.Professor;
import com.example.enaula.service.UrlFotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfessorMapper {

    private final UrlFotoService urlFotoService;

    public Professor toEntity(
            ProfessorRequestDTO dto
    ) {

        Professor professor = new Professor();

        professor.setNome(dto.nome());
        professor.setEmail(dto.email());
        professor.setSenha(dto.senha());
        professor.setPerfil("Professor");

        professor.setValorHoraAula(
                dto.valorHoraAula()
        );

        professor.setTitulacao(
                dto.titulacao()
        );

        return professor;
    }

    public ProfessorResponseDTO toResponseDTO(
            Professor professor
    ) {

        return new ProfessorResponseDTO(
                professor.getId(),
                professor.getNome(),
                professor.getEmail(),
                professor.getPerfil(),
                professor.getValorHoraAula(),
                professor.getTitulacao(),
                professor.getFoto(),
                urlFotoService.gerarUrl(professor.getFoto())
        );
    }

    public void updateEntity(
            Professor professor,
            ProfessorRequestDTO dto
    ) {

        professor.setNome(dto.nome());
        professor.setEmail(dto.email());

        professor.setValorHoraAula(
                dto.valorHoraAula()
        );

        professor.setTitulacao(
                dto.titulacao()
        );
    }

}