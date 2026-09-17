package com.example.enaula.mapper;

import com.example.enaula.dto.MateriaRequestDTO;
import com.example.enaula.dto.MateriaResponseDTO;
import com.example.enaula.entity.Materia;
import com.example.enaula.entity.Professor;
import org.springframework.stereotype.Component;

@Component
public class MateriaMapper {

    public Materia toEntity(
            MateriaRequestDTO dto,
            Professor professor
    ){
        Materia materia = new Materia();

        materia.setNome(dto.nome());
        materia.setDescicao(dto.descricao());
        materia.setProfessor(professor);

        return materia;
    }

    public MateriaResponseDTO toResponseDTO(Materia materia){

        return new MateriaResponseDTO(
                materia.getId(),
                materia.getNome(),
                materia.getDescicao(),
                materia.getProfessor().getId()
        );
    }

    public void updadeEntity(Materia materia, MateriaRequestDTO dto){
        materia.setNome(dto.nome());
        materia.setDescicao(dto.descricao());
    }
}
