package com.example.enaula.controller;

import com.example.enaula.dto.MaterialApoioResponseDTO;
import com.example.enaula.entity.Professor;
import com.example.enaula.service.MaterialApoioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/materiais")
@RequiredArgsConstructor
public class MaterialApoioController {

    private final MaterialApoioService materialApoioService;

    // ==========================
    // ENVIAR MATERIAL
    // POST
    // ==========================

    @PostMapping(value = "/aula/{aulaId}", consumes = "multipart/form-data")
    public ResponseEntity<MaterialApoioResponseDTO> enviar(
            @PathVariable Long aulaId,
            @RequestParam("arquivo") MultipartFile arquivo,
            @AuthenticationPrincipal Professor professor
    ) {

        MaterialApoioResponseDTO material =
                materialApoioService.enviarMaterial(aulaId, professor.getId(), arquivo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(material);
    }

    // ==========================
    // LISTAR POR AULA
    // GET
    // ==========================

    @GetMapping("/aula/{aulaId}")
    public ResponseEntity<List<MaterialApoioResponseDTO>> listarPorAula(
            @PathVariable Long aulaId
    ) {

        List<MaterialApoioResponseDTO> materiais =
                materialApoioService.listarPorAula(aulaId);

        return ResponseEntity.ok(materiais);
    }

    // ==========================
    // REMOVER MATERIAL
    // DELETE
    // ==========================

    @DeleteMapping("/{materialId}")
    public ResponseEntity<Void> remover(
            @PathVariable Long materialId,
            @AuthenticationPrincipal Professor professor
    ) {

        materialApoioService.removerMaterial(materialId, professor.getId());

        return ResponseEntity
                .noContent()
                .build();
    }
}
