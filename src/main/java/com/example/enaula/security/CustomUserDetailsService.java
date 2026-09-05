package com.example.enaula.security;

import com.example.enaula.entity.Aluno;
import com.example.enaula.entity.Professor;
import com.example.enaula.repository.AlunoRepository;
import com.example.enaula.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Aluno aluno = alunoRepository.findByEmail(email).orElse(null);

        if (aluno != null) {
            return aluno;
        }

        Professor professor = professorRepository.findByEmail(email).orElse(null);

        if (professor != null) {
            return professor;
        }

        throw new UsernameNotFoundException(
                "Usuário não encontrado com o email: " + email
        );
    }
}