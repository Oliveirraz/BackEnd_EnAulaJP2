package com.example.enaula.service;

import com.example.enaula.dto.LoginRequestDTO;
import com.example.enaula.dto.LoginResponseDTO;
import com.example.enaula.entity.Aluno;
import com.example.enaula.entity.Professor;
import com.example.enaula.repository.AlunoRepository;
import com.example.enaula.repository.ProfessorRepository;
import com.example.enaula.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponseDTO login(LoginRequestDTO dto) {

        String tipo = dto.tipo().toUpperCase();

        if (tipo.equals("ALUNO")) {
            return loginAluno(dto);
        }

        if (tipo.equals("PROFESSOR")) {
            return loginProfessor(dto);
        }

        throw new IllegalArgumentException(
                "Tipo de usuário inválido. Use ALUNO ou PROFESSOR."
        );
    }

    private LoginResponseDTO loginAluno(LoginRequestDTO dto) {

        Aluno aluno = alunoRepository.findByEmail(dto.email())
                .orElseThrow(() ->
                        new IllegalArgumentException("Email ou senha inválidos")
                );

        if (!passwordEncoder.matches(dto.senha(), aluno.getSenha())) {
            throw new IllegalArgumentException("Email ou senha inválidos");
        }

        String token = jwtService.gerarToken(
                aluno.getEmail(),
                "ALUNO"
        );

        return new LoginResponseDTO(
                token,
                "ALUNO",
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail()
        );
    }

    private LoginResponseDTO loginProfessor(LoginRequestDTO dto) {

        Professor professor = professorRepository.findByEmail(dto.email())
                .orElseThrow(() ->
                        new IllegalArgumentException("Email ou senha inválidos")
                );

        if (!passwordEncoder.matches(dto.senha(), professor.getSenha())) {
            throw new IllegalArgumentException("Email ou senha inválidos");
        }

        String token = jwtService.gerarToken(
                professor.getEmail(),
                "PROFESSOR"
        );

        return new LoginResponseDTO(
                token,
                "PROFESSOR",
                professor.getId(),
                professor.getNome(),
                professor.getEmail()
        );
    }
}