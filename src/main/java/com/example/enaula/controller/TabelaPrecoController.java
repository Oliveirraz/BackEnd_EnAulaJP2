package com.example.enaula.controller;

import com.example.enaula.dto.TabelaPrecoResponseDTO;
import com.example.enaula.service.TabelaPrecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/tabela-precos")
@RequiredArgsConstructor
public class TabelaPrecoController {

    private final TabelaPrecoService tabelaPrecoService;

    // LISTAR OS VALORES MÍNIMOS DA HORA-AULA POR TITULAÇÃO
    // (rota pública: o cadastro do professor acontece antes do login)
    @GetMapping
    public List<TabelaPrecoResponseDTO> listar() {
        return tabelaPrecoService.listar();
    }
}