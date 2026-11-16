package com.example.enaula.security;

import com.example.enaula.entity.Aluno;
import com.example.enaula.entity.Professor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

// Confere se quem fez a requisição é o dono do perfil que está sendo acessado.
// É usado nas anotações @PreAuthorize dos controllers, como:
//   @PreAuthorize("@donoDoPerfil.ehProfessor(authentication, #id)")
//
// A conferência olha o TIPO da conta além do id, porque aluno e professor ficam em
// tabelas diferentes: o aluno de id 5 não pode mexer no professor de id 5.
@Component("donoDoPerfil")
public class DonoDoPerfil {

    public boolean ehProfessor(Authentication autenticacao, Long id) {
        return autenticacao != null
                && autenticacao.getPrincipal() instanceof Professor professor
                && professor.getId() != null
                && professor.getId().equals(id);
    }

    public boolean ehAluno(Authentication autenticacao, Long id) {
        return autenticacao != null
                && autenticacao.getPrincipal() instanceof Aluno aluno
                && aluno.getId() != null
                && aluno.getId().equals(id);
    }
}