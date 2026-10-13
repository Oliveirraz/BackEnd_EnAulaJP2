package com.example.enaula.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Trata exceções de recurso não encontrado.
    // Retorna HTTP 404 (NOT_FOUND) quando um recurso solicitado,
    // como um aluno, professor ou aula, não é encontrado.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Trata exceções de acesso negado.
    // Retorna HTTP 403 (FORBIDDEN) quando o usuário não possui
    // permissão para acessar ou executar determinada operação.
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    // Trata argumentos inválidos fornecidos durante uma operação.
    // Retorna HTTP 400 (BAD_REQUEST) quando um argumento recebido
    // ou utilizado pelo sistema é inválido.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
