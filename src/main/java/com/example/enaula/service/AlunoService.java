package com.example.enaula.service;

import com.example.enaula.dto.AlunoRequestDTO;
import com.example.enaula.dto.AlunoResponseDTO;
import com.example.enaula.entity.Aluno;
import com.example.enaula.exception.ResourceNotFoundException;
import com.example.enaula.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlunoService {
    private final AlunoRepository alunoRepository;

    // CRIAR ALUNO
    public AlunoResponseDTO criarAluno(AlunoRequestDTO dto) {

        if (alunoRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Aluno aluno = new Aluno();
        aluno.setNome(dto.nome());
        aluno.setEmail(dto.email());
        aluno.setSenha(dto.senha()); // sem criptografia por enquanto

        Aluno salvo = alunoRepository.save(aluno);

        return toResponseDTO(salvo);
    }

    // BUSCAR POR ID
    @Transactional(readOnly = true)
    public AlunoResponseDTO buscarAlunoPorId(Long id) {

        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado"));

        return toResponseDTO(aluno);
    }

    // CONVERSÃO PARA DTO
    private AlunoResponseDTO toResponseDTO(Aluno aluno) {
        return new AlunoResponseDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getFoto()
        );
    }

    // ATUALIZAR ALUNO
    public AlunoResponseDTO atualizarAluno(Long id, AlunoRequestDTO dto) {

        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado"));

        if (dto.nome() != null && !dto.nome().isBlank()) {
            aluno.setNome(dto.nome());
        }

        if (dto.email() != null && !dto.email().isBlank()) {
            aluno.setEmail(dto.email());
        }

        if (dto.senha() != null && !dto.senha().isBlank()) {
            aluno.setSenha(dto.senha()); // sem criptografia por enquanto
        }

        Aluno atualizado = alunoRepository.save(aluno);

        return toResponseDTO(atualizado);
    }

    // DELETAR ALUNO
    public void deletarAluno(Long id) {

        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado"));

        alunoRepository.delete(aluno);
    }

}
