package com.example.enaula.service;

import com.example.enaula.dto.AlunoRequestDTO;
import com.example.enaula.dto.AlunoResponseDTO;
import com.example.enaula.entity.Aluno;
import com.example.enaula.exception.ResourceNotFoundException;
import com.example.enaula.mapper.AlunoMapper;
import com.example.enaula.repository.AlunoRepository;
import com.example.enaula.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final PasswordEncoder passwordEncoder;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final AlunoMapper alunoMapper;

    // ==========================
    // CRIAR ALUNO
    // ==========================

    public AlunoResponseDTO criarAluno(AlunoRequestDTO dto) {

        if (alunoRepository.findByEmail(dto.email()).isPresent()
                || professorRepository.findByEmail(dto.email()).isPresent()) {

            throw new IllegalArgumentException("Email já cadastrado");
        }

        Aluno aluno = alunoMapper.toEntity(dto);

        // Criptografa a senha antes de salvar
        aluno.setSenha(
                passwordEncoder.encode(aluno.getSenha())
        );

        Aluno salvo = alunoRepository.save(aluno);

        return alunoMapper.toResponseDTO(salvo);
    }

    // ==========================
    // BUSCAR POR ID
    // ==========================

    @Transactional(readOnly = true)
    public AlunoResponseDTO buscarAlunoPorId(Long id) {

        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Aluno não encontrado"
                        )
                );

        return alunoMapper.toResponseDTO(aluno);
    }

    // ==========================
    // ATUALIZAR ALUNO
    // ==========================

    public AlunoResponseDTO atualizarAluno(
            Long id,
            AlunoRequestDTO dto
    ) {

        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Aluno não encontrado"
                        )
                );

        // Atualiza nome e e-mail
        alunoMapper.updateEntity(aluno, dto);

        // Criptografa a nova senha antes de salvar
        aluno.setSenha(
                passwordEncoder.encode(dto.senha())
        );

        Aluno atualizado = alunoRepository.save(aluno);

        return alunoMapper.toResponseDTO(atualizado);
    }

    // ==========================
    // DELETAR ALUNO
    // ==========================

    public void deletarAluno(Long id) {

        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Aluno não encontrado"
                        )
                );

        alunoRepository.delete(aluno);
    }
}