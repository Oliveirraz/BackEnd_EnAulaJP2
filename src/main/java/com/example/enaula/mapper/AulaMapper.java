package com.example.enaula.mapper;

import com.example.enaula.dto.AulaRequestDTO;
import com.example.enaula.dto.AulaResponseDTO;
import com.example.enaula.entity.Aula;
import com.example.enaula.entity.Materia;
import com.example.enaula.entity.Professor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

        aula.setFormato(dto.formato());
        aula.setValorAula(dto.valorAula());
        aula.setQuantidadeParticipantes(
                dto.quantidadeParticipantes()
        );

        return aula;
    }

    public AulaResponseDTO toResponseDTO(
            Aula aula
    ) {

        Integer participantes =
                aula.getQuantidadeParticipantes();

        BigDecimal valorPorAluno = null;

        if (aula.getValorAula() != null
                && participantes != null
                && participantes > 0) {

            valorPorAluno =
                    aula.getValorAula()
                            .divide(
                                    BigDecimal.valueOf(participantes),
                                    2,
                                    RoundingMode.HALF_UP
                            );
        }

        return new AulaResponseDTO(
                aula.getId(),
                aula.getMateria().getId(),
                aula.getMateria().getNome(),
                aula.getProfessor().getId(),
                aula.getData(),
                aula.getHorario(),
                aula.getDuracao(),
                aula.getModalidade(),
                aula.getFormato(),
                aula.getValorAula(),
                participantes,
                valorPorAluno
        );
    }
}