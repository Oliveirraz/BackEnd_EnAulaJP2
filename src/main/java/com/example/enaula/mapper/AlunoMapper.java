package com.example.enaula.mapper;

import com.example.enaula.dto.AlunoRequestDTO;
import com.example.enaula.dto.AlunoResponseDTO;
import com.example.enaula.entity.Aluno;
import org.springframework.stereotype.Component;

@Component
public class AlunoMapper {

    public Aluno toEntity(AlunoRequestDTO dto) {

        Aluno aluno = new Aluno();

        aluno.setNome(dto.nome());
        aluno.setEmail(dto.email());
        aluno.setSenha(dto.senha());

        return aluno;
    }

    public AlunoResponseDTO toResponseDTO(Aluno aluno) {

        return new AlunoResponseDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getFoto()
        );
    }

    public void updateEntity(
            Aluno aluno,
            AlunoRequestDTO dto) {

        aluno.setNome(dto.nome());
        aluno.setEmail(dto.email());
        aluno.setSenha(dto.senha());
    }
}