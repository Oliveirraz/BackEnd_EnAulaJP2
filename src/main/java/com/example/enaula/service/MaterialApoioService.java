
package com.example.enaula.service;

import com.example.enaula.dto.MaterialApoioResponseDTO;
import com.example.enaula.entity.Aula;
import com.example.enaula.entity.MaterialApoio;
import com.example.enaula.entity.Professor;
import com.example.enaula.entity.Aluno;
import com.example.enaula.entity.Matricula;
import com.example.enaula.entity.StatusMatricula;
import com.example.enaula.exception.ResourceNotFoundException;
import com.example.enaula.mapper.MaterialApoioMapper;
import com.example.enaula.repository.AulaRepository;
import com.example.enaula.repository.MaterialApoioRepository;
import com.example.enaula.repository.ProfessorRepository;
import com.example.enaula.repository.MatriculaRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.GetObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class MaterialApoioService {

    private final MinioClient minioClient;
    private final MaterialApoioRepository materialApoioRepository;
    private final AulaRepository aulaRepository;
    private final ProfessorRepository professorRepository;
    private final MaterialApoioMapper materialApoioMapper;
    private final MatriculaRepository matriculaRepository;
    private final EmailService emailService;

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

    public MaterialApoioService(
            MinioClient minioClient,
            MaterialApoioRepository materialApoioRepository,
            AulaRepository aulaRepository,
            ProfessorRepository professorRepository,
            MaterialApoioMapper materialApoioMapper,
            MatriculaRepository matriculaRepository,
            EmailService emailService
    ) {
        this.minioClient = minioClient;
        this.materialApoioRepository = materialApoioRepository;
        this.aulaRepository = aulaRepository;
        this.professorRepository = professorRepository;
        this.materialApoioMapper = materialApoioMapper;
        this.matriculaRepository = matriculaRepository;
        this.emailService = emailService;
    }

    @Transactional
    public MaterialApoioResponseDTO enviarMaterial(
            Long aulaId,
            Long professorIdAutenticado,
            MultipartFile arquivo
    ) {
        if (arquivo.isEmpty()) {
            throw new IllegalArgumentException("Arquivo vazio.");
        }

        String tipoConteudo = arquivo.getContentType();

        if (tipoConteudo == null
                || !TIPOS_PERMITIDOS.contains(tipoConteudo)) {
            throw new IllegalArgumentException(
                    "Tipo de arquivo não permitido: " + tipoConteudo
            );
        }

        Aula aula = aulaRepository.findById(aulaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Aula não encontrada: " + aulaId
                        )
                );

        Professor professor = professorRepository.findById(
                professorIdAutenticado
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Professor não encontrado: "
                                + professorIdAutenticado
                )
        );

        String chaveObjeto = UUID.randomUUID()
                + "_" + arquivo.getOriginalFilename();

        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(chaveObjeto)
                            .stream(
                                    arquivo.getInputStream(),
                                    arquivo.getSize(),
                                    -1
                            )
                            .contentType(tipoConteudo)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(
                    "Falha ao enviar arquivo para o MinIO.",
                    e
            );
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

        MaterialApoio salvo =
                materialApoioRepository.save(material);

        // NOTIFICA OS ALUNOS COM MATRÍCULA ACEITA
        List<Matricula> matriculasAceitas =
                matriculaRepository
                        .findAllByAulaAndStatusOrderByDataSolicitacaoAsc(
                                aula,
                                StatusMatricula.ACEITA
                        );

        String assunto =
                "Novo material disponível - "
                        + aula.getMateria().getNome();

        String mensagemBase =
                "Um novo material de apoio foi disponibilizado pelo professor "
                        + professor.getNome()
                        + ".\n\nMatéria: "
                        + aula.getMateria().getNome()
                        + "\nAula: "
                        + aula.getData()
                        + " às " + aula.getHorario()
                        + "\nMaterial: "
                        + salvo.getNomeOriginal()
                        + "\n\nAcesse sua área de matérias no EnAula "
                        + "para visualizar e baixar o arquivo."
                        + "\n\nEquipe EnAula";

        for (Matricula matriculaAceita : matriculasAceitas) {
            Aluno aluno = matriculaAceita.getAluno();

            if (aluno != null
                    && aluno.getEmail() != null
                    && !aluno.getEmail().isBlank()) {
                emailService.enviarEmail(
                        aluno.getEmail(),
                        assunto,
                        mensagemBase
                );
            }
        }

        return materialApoioMapper.toResponseDTO(salvo);
    }

    public List<MaterialApoioResponseDTO> listarPorAula(
            Long aulaId
    ) {
        return materialApoioRepository
                .findByAulaId(aulaId)
                .stream()
                .map(materialApoioMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MaterialApoioResponseDTO> listarMeusMateriais(
            Aluno aluno
    ) {
        return materialApoioRepository
                .listarMateriaisDoAluno(aluno)
                .stream()
                .map(materialApoioMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public MaterialApoio buscarMaterialAutorizado(
            Long materialId,
            Object usuarioAutenticado
    ) {
        MaterialApoio material =
                materialApoioRepository.findById(materialId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Material não encontrado: "
                                                + materialId
                                )
                        );

        if (usuarioAutenticado instanceof Professor professor) {
            if (!material.getProfessor().getId()
                    .equals(professor.getId())) {
                throw new AccessDeniedException(
                        "Você não tem permissão para baixar este material."
                );
            }

            return material;
        }

        if (usuarioAutenticado instanceof Aluno aluno) {
            boolean matriculaAceita =
                    matriculaRepository
                            .findByAulaAndAlunoAndStatus(
                                    material.getAula(),
                                    aluno,
                                    StatusMatricula.ACEITA
                            )
                            .isPresent();

            if (!matriculaAceita) {
                throw new AccessDeniedException(
                        "Você precisa ter matrícula aceita "
                                + "nesta aula para baixar o material."
                );
            }

            return material;
        }

        throw new AccessDeniedException(
                "Usuário não autorizado a baixar materiais."
        );
    }

    public io.minio.GetObjectResponse abrirArquivo(
            MaterialApoio material
    ) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(material.getNomeBucket())
                        .object(material.getChaveObjeto())
                        .build()
        );
    }

    @Transactional
    public void removerMaterial(
            Long materialId,
            Long professorIdAutenticado
    ) {
        MaterialApoio material =
                materialApoioRepository.findById(materialId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Material não encontrado: "
                                                + materialId
                                )
                        );

        if (!material.getProfessor().getId()
                .equals(professorIdAutenticado)) {
            throw new AccessDeniedException(
                    "Você não tem permissão para remover este material."
            );
        }

        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(material.getNomeBucket())
                            .object(material.getChaveObjeto())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(
                    "Falha ao remover arquivo do MinIO.",
                    e
            );
        }

        materialApoioRepository.delete(material);
    }
}
