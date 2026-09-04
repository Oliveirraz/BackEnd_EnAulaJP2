package com.example.enaula.service;

import com.example.enaula.dto.AlunoRequestDTO;
import com.example.enaula.dto.AlunoResponseDTO;
import com.example.enaula.entity.Aluno;
import com.example.enaula.exception.ResourceNotFoundException;
import com.example.enaula.mapper.AlunoMapper;
import com.example.enaula.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final AlunoMapper alunoMapper;

    // CRIAR ALUNO
    public AlunoResponseDTO criarAluno(AlunoRequestDTO dto) {

        if (alunoRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        // Converte DTO para entidade usando o Mapper
        Aluno aluno = alunoMapper.toEntity(dto);

        // Salva no banco
        Aluno salvo = alunoRepository.save(aluno);

        // Converte entidade para DTO de resposta
        return alunoMapper.toResponseDTO(salvo);
    }

    // BUSCAR POR ID
    @Transactional(readOnly = true)
    public AlunoResponseDTO buscarAlunoPorId(Long id) {

        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Aluno não encontrado"));

        return alunoMapper.toResponseDTO(aluno);
    }

    // ATUALIZAR ALUNO
    public AlunoResponseDTO atualizarAluno(
            Long id,
            AlunoRequestDTO dto) {

        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Aluno não encontrado"));

        // Atualiza a entidade usando o Mapper
        alunoMapper.updateEntity(aluno, dto);

        Aluno atualizado = alunoRepository.save(aluno);

        return alunoMapper.toResponseDTO(atualizado);
    }

    // DELETAR ALUNO
    public void deletarAluno(Long id) {

        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Aluno não encontrado"));

        alunoRepository.delete(aluno);
    }
}

