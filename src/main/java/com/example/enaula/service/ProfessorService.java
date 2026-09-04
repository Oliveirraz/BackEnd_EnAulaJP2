package com.example.enaula.service;

import com.example.enaula.dto.ProfessorRequestDTO;
import com.example.enaula.dto.ProfessorResponseDTO;
import com.example.enaula.entity.Professor;
import com.example.enaula.mapper.ProfessorMapper;
import com.example.enaula.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    private final ProfessorMapper professorMapper;


    // ==========================
    // CADASTRAR PROFESSOR
    // ==========================

    public ProfessorResponseDTO cadastrarProfessor(
            ProfessorRequestDTO dto
    ) {

        Professor professor =
                professorMapper.toEntity(dto);

        Professor professorSalvo =
                professorRepository.save(professor);

        return professorMapper
                .toResponseDTO(professorSalvo);
    }


    // ==========================
    // LISTAR PROFESSORES
    // ==========================

    public List<ProfessorResponseDTO> listarProfessores() {

        return professorRepository
                .findAll()
                .stream()
                .map(professorMapper::toResponseDTO)
                .toList();
    }


    // ==========================
    // BUSCAR POR ID
    // ==========================

    public ProfessorResponseDTO buscarPorId(Long id) {

        Professor professor =
                professorRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Professor não encontrado"
                                )
                        );

        return professorMapper
                .toResponseDTO(professor);
    }


    // ==========================
    // ATUALIZAR PROFESSOR
    // ==========================

    public ProfessorResponseDTO atualizarProfessor(

            Long id,

            ProfessorRequestDTO dto

    ) {

        Professor professor =
                professorRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Professor não encontrado"
                                )
                        );


        professorMapper.updateEntity(
                professor,
                dto
        );


        Professor professorAtualizado =
                professorRepository.save(professor);


        return professorMapper
                .toResponseDTO(professorAtualizado);
    }


    // ==========================
    // DELETAR PROFESSOR
    // ==========================

    public void deletarProfessor(Long id) {

        Professor professor =
                professorRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Professor não encontrado"
                                )
                        );


        professorRepository.delete(professor);

    }

}