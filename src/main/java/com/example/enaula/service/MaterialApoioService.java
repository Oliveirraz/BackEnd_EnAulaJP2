package com.example.enaula.service;

import com.example.enaula.dto.MaterialApoioResponseDTO;
import com.example.enaula.entity.Aula;
import com.example.enaula.entity.MaterialApoio;
import com.example.enaula.entity.Professor;
import com.example.enaula.exception.ResourceNotFoundException;
import com.example.enaula.mapper.MaterialApoioMapper;
import com.example.enaula.repository.AulaRepository;
import com.example.enaula.repository.MaterialApoioRepository;
import com.example.enaula.repository.ProfessorRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class MaterialApoioService {

    private final MinioClient minioClient;
    private final MaterialApoioRepository materialApoioRepository;
    private final AulaRepository aulaRepository;
    private final ProfessorRepository professorRepository;
    private final MaterialApoioMapper materialApoioMapper;

    @Value("${minio.bucket.materiais}")
    private String bucketName;

    private static final List<String> TIPOS_PERMITIDOS = List.of(
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "text/plain"
    );

    public MaterialApoioService(MinioClient minioClient,
                                MaterialApoioRepository materialApoioRepository,
                                AulaRepository aulaRepository,
                                ProfessorRepository professorRepository,
                                MaterialApoioMapper materialApoioMapper) {
        this.minioClient = minioClient;
        this.materialApoioRepository = materialApoioRepository;
        this.aulaRepository = aulaRepository;
        this.professorRepository = professorRepository;
        this.materialApoioMapper = materialApoioMapper;
    }

    @Transactional
    public MaterialApoioResponseDTO enviarMaterial(Long aulaId, Long professorIdAutenticado, MultipartFile arquivo) {

        if (arquivo.isEmpty()) {
            throw new IllegalArgumentException("Arquivo vazio.");
        }

        String tipoConteudo = arquivo.getContentType();
        if (tipoConteudo == null || !TIPOS_PERMITIDOS.contains(tipoConteudo)) {
            throw new IllegalArgumentException("Tipo de arquivo não permitido: " + tipoConteudo);
        }

        Aula aula = aulaRepository.findById(aulaId)
                .orElseThrow(() -> new ResourceNotFoundException("Aula não encontrada: " + aulaId));

        Professor professor = professorRepository.findById(professorIdAutenticado)
                .orElseThrow(() -> new ResourceNotFoundException("Professor não encontrado: " + professorIdAutenticado));

        String chaveObjeto = UUID.randomUUID() + "_" + arquivo.getOriginalFilename();

        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(chaveObjeto)
                            .stream(arquivo.getInputStream(), arquivo.getSize(), -1)
                            .contentType(tipoConteudo)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Falha ao enviar arquivo para o MinIO.", e);
        }

        MaterialApoio material = new MaterialApoio(
                null,
                aula,
                professor,
                arquivo.getOriginalFilename(),
                chaveObjeto,
                bucketName,
                tipoConteudo,
                arquivo.getSize(),
                java.time.LocalDateTime.now()
        );

        MaterialApoio salvo = materialApoioRepository.save(material);

        return materialApoioMapper.toResponseDTO(salvo);
    }

    public List<MaterialApoioResponseDTO> listarPorAula(Long aulaId) {
        return materialApoioRepository.findByAulaId(aulaId)
                .stream()
                .map(materialApoioMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public void removerMaterial(Long materialId, Long professorIdAutenticado) {

        MaterialApoio material = materialApoioRepository.findById(materialId)
                .orElseThrow(() -> new ResourceNotFoundException("Material não encontrado: " + materialId));

        if (!material.getProfessor().getId().equals(professorIdAutenticado)) {
            throw new AccessDeniedException("Você não tem permissão para remover este material.");
        }

        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(material.getNomeBucket())
                            .object(material.getChaveObjeto())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Falha ao remover arquivo do MinIO.", e);
        }

        materialApoioRepository.delete(material);
    }
}
