package com.example.enaula.service;

import com.example.enaula.dto.AlunoRequestDTO;
import com.example.enaula.dto.AlunoResponseDTO;
import com.example.enaula.entity.Aluno;
import com.example.enaula.exception.ResourceNotFoundException;
import com.example.enaula.mapper.AlunoMapper;
import com.example.enaula.repository.AlunoRepository;
import com.example.enaula.repository.ProfessorRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final PasswordEncoder passwordEncoder;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final AlunoMapper alunoMapper;
    private final MinioClient minioClient;

    @Value("${minio.bucket.fotos}")
    private String bucketFotos;

    private static final List<String> TIPOS_IMAGEM_PERMITIDOS = List.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    private static final long TAMANHO_MAXIMO_BYTES = 2 * 1024 * 1024; // 2MB

    // ==========================
    // CRIAR ALUNO
    // ==========================

    @Transactional
    public AlunoResponseDTO criarAluno(AlunoRequestDTO dto, MultipartFile foto) {

        if (alunoRepository.findByEmail(dto.email()).isPresent()
                || professorRepository.findByEmail(dto.email()).isPresent()) {

            throw new IllegalArgumentException("Email já cadastrado");
        }

        Aluno aluno = alunoMapper.toEntity(dto);
        aluno.setSenha(passwordEncoder.encode(aluno.getSenha()));

        Aluno salvo = alunoRepository.save(aluno);

        if (foto != null && !foto.isEmpty()) {
            return enviarFoto(salvo.getId(), foto);
        }

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

    // ==========================
    // ENVIAR FOTO DE PERFIL
    // ==========================

    @Transactional
    public AlunoResponseDTO enviarFoto(Long alunoId, MultipartFile arquivo) {

        if (arquivo.isEmpty()) {
            throw new IllegalArgumentException("Arquivo vazio.");
        }

        String tipoConteudo = arquivo.getContentType();
        if (tipoConteudo == null || !TIPOS_IMAGEM_PERMITIDOS.contains(tipoConteudo)) {
            throw new IllegalArgumentException("Tipo de imagem não permitido: " + tipoConteudo);
        }

        if (arquivo.getSize() > TAMANHO_MAXIMO_BYTES) {
            throw new IllegalArgumentException("Imagem muito grande. Tamanho máximo: 2MB.");
        }

        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Aluno não encontrado: " + alunoId)
                );

        // Remove a foto antiga do MinIO, se existir, para não acumular lixo
        if (aluno.getFoto() != null && !aluno.getFoto().isBlank()) {
            removerArquivoSilenciosamente(aluno.getFoto());
        }

        String chaveObjeto = "alunos/" + alunoId + "/" + UUID.randomUUID() + "_" + arquivo.getOriginalFilename();

        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketFotos)
                            .object(chaveObjeto)
                            .stream(arquivo.getInputStream(), arquivo.getSize(), -1)
                            .contentType(tipoConteudo)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Falha ao enviar foto para o MinIO.", e);
        }

        aluno.setFoto(chaveObjeto);
        Aluno atualizado = alunoRepository.save(aluno);

        return alunoMapper.toResponseDTO(atualizado);
    }

    // ==========================
    // REMOVER FOTO DE PERFIL
    // ==========================

    @Transactional
    public AlunoResponseDTO removerFoto(Long alunoId) {

        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Aluno não encontrado: " + alunoId)
                );

        if (aluno.getFoto() != null && !aluno.getFoto().isBlank()) {
            removerArquivoSilenciosamente(aluno.getFoto());
            aluno.setFoto(null);
            alunoRepository.save(aluno);
        }

        return alunoMapper.toResponseDTO(aluno);
    }

    private void removerArquivoSilenciosamente(String chaveObjeto) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketFotos)
                            .object(chaveObjeto)
                            .build()
            );
        } catch (Exception e) {
            // Se a foto antiga já não existe no MinIO por algum motivo,
            // não faz sentido travar a troca por causa disso.
        }
    }
}