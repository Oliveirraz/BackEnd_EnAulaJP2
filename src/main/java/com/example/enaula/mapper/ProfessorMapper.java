package com.example.enaula.mapper;

import com.example.enaula.RequestDTO.ProfessorRequestDTO;
import com.example.enaula.ResponseDTO.ProfessorResponseDTO;
import com.example.enaula.entity.Professor;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper {

    public Professor toEntity(ProfessorRequestDTO dto) {

        Professor professor = new Professor();

        professor.setNome(dto.nome());
        professor.setEmail(dto.email());
        professor.setSenha(dto.senha());
        professor.setPerfil("Professor");
        professor.setValorHoraAula(dto.valorHoraAula());

        return professor;
    }


    public ProfessorResponseDTO toResponseDTO(Professor professor) {

        return new ProfessorResponseDTO(

                professor.getId(),
                professor.getNome(),
                professor.getEmail(),
                professor.getPerfil(),
                professor.getValorHoraAula()

        );
    }


    public void updateEntity(
            Professor professor,
            ProfessorRequestDTO dto
    ) {

        professor.setNome(dto.nome());
        professor.setEmail(dto.email());
        professor.setSenha(dto.senha());
        professor.setValorHoraAula(dto.valorHoraAula());

    }

}