package com.example.enaula.mapper;

import com.example.enaula.dto.AulaRequestDTO;
import com.example.enaula.dto.AulaResponseDTO;
import com.example.enaula.entity.Aula;
import com.example.enaula.entity.Materia;
import com.example.enaula.entity.Professor;
import org.springframework.stereotype.Component;

@Component
public class AulaMapper {

    public Aula toEntity(
            AulaRequestDTO dto,
            Materia materia,
            Professor professor
    ) {

        Aula aula = new Aula();

        aula.setMateria(materia);
        aula.setProfessor(professor);
        aula.setData(dto.data());
        aula.setHorario(dto.horario());
        aula.setDuracao(dto.duracao());
        aula.setModalidade(dto.modalidade());

        return aula;
    }

    public AulaResponseDTO toResponseDTO(Aula aula) {

        return new AulaResponseDTO(
                aula.getId(),
                aula.getMateria().getId(),
                aula.getMateria().getNome(),
                aula.getProfessor().getId(),
                aula.getData(),
                aula.getHorario(),
                aula.getDuracao(),
                aula.getModalidade()
        );
    }
}
