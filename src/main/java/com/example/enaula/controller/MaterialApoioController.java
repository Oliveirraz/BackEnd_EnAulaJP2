
package com.example.enaula.controller;

import com.example.enaula.dto.MaterialApoioResponseDTO;
import com.example.enaula.entity.Professor;
import com.example.enaula.entity.Aluno;
import com.example.enaula.entity.MaterialApoio;
import com.example.enaula.service.MaterialApoioService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.nio.charset.StandardCharsets;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/materiais")
@RequiredArgsConstructor
public class MaterialApoioController {

    private final MaterialApoioService materialApoioService;

    // ENVIAR MATERIAL
    @PostMapping(
            value = "/aula/{aulaId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<MaterialApoioResponseDTO> enviar(
            @PathVariable Long aulaId,
            @RequestParam("arquivo") MultipartFile arquivo,
            @AuthenticationPrincipal Professor professor
    ) {

        MaterialApoioResponseDTO material =
                materialApoioService.enviarMaterial(
                        aulaId,
                        professor.getId(),
                        arquivo
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(material);
    }

    // LISTAR MATERIAIS DE UMA AULA
    @GetMapping("/aula/{aulaId}")
    public ResponseEntity<List<MaterialApoioResponseDTO>> listarPorAula(
            @PathVariable Long aulaId
    ) {

        List<MaterialApoioResponseDTO> materiais =
                materialApoioService.listarPorAula(aulaId);

        return ResponseEntity.ok(materiais);
    }

    // LISTAR MATERIAIS DISPONÍVEIS AO ALUNO
    @GetMapping("/meus")
    public ResponseEntity<List<MaterialApoioResponseDTO>> listarMeus(
            @AuthenticationPrincipal Aluno aluno
    ) {

        return ResponseEntity.ok(
                materialApoioService.listarMeusMateriais(aluno)
        );
    }

    // DOWNLOAD PROTEGIDO
    @GetMapping("/{materialId}/download")
    public ResponseEntity<StreamingResponseBody> baixar(
            @PathVariable Long materialId,
            @AuthenticationPrincipal Object usuario
    ) {

        MaterialApoio material =
                materialApoioService.buscarMaterialAutorizado(
                        materialId,
                        usuario
                );

        StreamingResponseBody stream = output -> {

            try (var arquivo =
                         materialApoioService.abrirArquivo(material)) {

                arquivo.transferTo(output);

            } catch (Exception e) {

                throw new java.io.IOException(
                        "Erro ao realizar download do material.",
                        e
                );
            }
        };

        // Evita problemas com nomes de arquivos inválidos.
        String nomeSeguro = material.getNomeOriginal() == null
                ? "material"
                : material.getNomeOriginal()
                .replaceAll("[\\r\\n]", "_")
                .replace("\"", "_");

        ContentDisposition disposicao =
                ContentDisposition.attachment()
                        .filename(
                                nomeSeguro,
                                StandardCharsets.UTF_8
                        )
                        .build();

        MediaType tipoConteudo;

        try {
            tipoConteudo = MediaType.parseMediaType(
                    material.getTipoConteudo()
            );
        } catch (Exception e) {
            tipoConteudo = MediaType.APPLICATION_OCTET_STREAM;
        }

        return ResponseEntity.ok()
                .contentType(tipoConteudo)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        disposicao.toString()
                )
                .contentLength(material.getTamanhoBytes())
                .body(stream);
    }

    // REMOVER MATERIAL
    @DeleteMapping("/{materialId}")
    public ResponseEntity<Void> remover(
            @PathVariable Long materialId,
            @AuthenticationPrincipal Professor professor
    ) {

        materialApoioService.removerMaterial(
                materialId,
                professor.getId()
        );

        return ResponseEntity.noContent().build();
    }
}
