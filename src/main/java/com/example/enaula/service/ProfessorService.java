
package com.example.enaula.service;

import com.example.enaula.dto.ProfessorRequestDTO;
import com.example.enaula.dto.ProfessorResponseDTO;
import com.example.enaula.entity.Professor;
import com.example.enaula.exception.ResourceNotFoundException;
import com.example.enaula.mapper.ProfessorMapper;
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
public class ProfessorService {

    private final PasswordEncoder passwordEncoder;
    private final ProfessorRepository professorRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorMapper professorMapper;
    private final MinioClient minioClient;

    @Value("${minio.bucket.fotos}")
    private String bucketFotos;

    private static final List<String> TIPOS_IMAGEM_PERMITIDOS =
            List.of(
                    "image/jpeg",
                    "image/png",
                    "image/webp"
            );

    private static final long TAMANHO_MAXIMO_BYTES =
            2 * 1024 * 1024; // 2 MB

    // CADASTRAR PROFESSOR COM FOTO OPCIONAL
    @Transactional
    public ProfessorResponseDTO cadastrarProfessor(
            ProfessorRequestDTO dto,
            MultipartFile foto
    ) {

        // Impede o cadastro de e-mails já utilizados.
        if (professorRepository.findByEmail(dto.email()).isPresent()
                || alunoRepository.findByEmail(dto.email()).isPresent()) {

            throw new IllegalArgumentException(
                    "Email já cadastrado"
            );
        }

        // Converte os dados recebidos em uma entidade Professor.
        Professor professor = professorMapper.toEntity(dto);

        // Criptografa a senha antes de salvar.
        professor.setSenha(
                passwordEncoder.encode(dto.senha())
        );

        // Salva o professor para gerar seu ID.
        Professor salvo = professorRepository.save(professor);

        // Se uma foto foi enviada, armazena no MinIO.
        if (foto != null && !foto.isEmpty()) {
            return enviarFoto(salvo.getId(), foto);
        }

        return professorMapper.toResponseDTO(salvo);
    }

    // LISTAR PROFESSORES
    @Transactional(readOnly = true)
    public List<ProfessorResponseDTO> listarProfessores() {

        return professorRepository.findAll()
                .stream()
                .map(professorMapper::toResponseDTO)
                .toList();
    }

    // BUSCAR PROFESSOR POR ID
    @Transactional(readOnly = true)
    public ProfessorResponseDTO buscarPorId(Long id) {

        Professor professor = professorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Professor não encontrado: " + id
                        )
                );

        return professorMapper.toResponseDTO(professor);
    }

    // ATUALIZAR DADOS DO PROFESSOR
    @Transactional
    public ProfessorResponseDTO atualizarProfessor(
            Long id,
            ProfessorRequestDTO dto
    ) {

        Professor professor = professorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Professor não encontrado: " + id
                        )
                );

        // Verifica se o novo e-mail pertence a outra pessoa.
        professorRepository.findByEmail(dto.email())
                .filter(outro -> !outro.getId().equals(id))
                .ifPresent(outro -> {
                    throw new IllegalArgumentException(
                            "Email já cadastrado"
                    );
                });

        alunoRepository.findByEmail(dto.email())
                .ifPresent(outro -> {
                    throw new IllegalArgumentException(
                            "Email já cadastrado por um aluno"
                    );
                });

        // Atualiza nome, e-mail, valor da aula e titulação.
        professorMapper.updateEntity(professor, dto);

        // Criptografa a senha nova antes de salvar.
        professor.setSenha(
                passwordEncoder.encode(dto.senha())
        );

        Professor atualizado =
                professorRepository.save(professor);

        return professorMapper.toResponseDTO(atualizado);
    }

    // DELETAR PROFESSOR
    @Transactional
    public void deletarProfessor(Long id) {

        Professor professor = professorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Professor não encontrado: " + id
                        )
                );

        // Remove a foto do armazenamento, se houver.
        if (professor.getFoto() != null
                && !professor.getFoto().isBlank()) {

            removerArquivoSilenciosamente(professor.getFoto());
        }

        professorRepository.delete(professor);
    }

    // ENVIAR OU TROCAR FOTO DE PERFIL
    @Transactional
    public ProfessorResponseDTO enviarFoto(
            Long professorId,
            MultipartFile arquivo
    ) {

        if (arquivo == null || arquivo.isEmpty()) {
            throw new IllegalArgumentException(
                    "Selecione uma imagem."
            );
        }

        // Valida o tipo de arquivo.
        String tipoConteudo = arquivo.getContentType();

        if (tipoConteudo == null
                || !TIPOS_IMAGEM_PERMITIDOS.contains(tipoConteudo)) {

            throw new IllegalArgumentException(
                    "Tipo de imagem não permitido. Use JPG, PNG ou WEBP."
            );
        }

        // Limita a imagem a 2 MB.
        if (arquivo.getSize() > TAMANHO_MAXIMO_BYTES) {
            throw new IllegalArgumentException(
                    "Imagem muito grande. Tamanho máximo: 2 MB."
            );
        }

        Professor professor =
                professorRepository.findById(professorId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Professor não encontrado: "
                                                + professorId
                                )
                        );

        // Cria um nome único para evitar conflitos entre arquivos.
        String nomeOriginal = arquivo.getOriginalFilename();

        String extensao = "imagem";

        if (nomeOriginal != null
                && nomeOriginal.lastIndexOf('.') >= 0) {

            extensao = nomeOriginal.substring(
                    nomeOriginal.lastIndexOf('.') + 1
            ).replaceAll("[^a-zA-Z0-9]", "");
        }

        String chaveObjeto =
                "professores/" + professorId + "/"
                        + UUID.randomUUID() + "." + extensao;

        // Envia a imagem ao MinIO.
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketFotos)
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
                    "Falha ao enviar foto do professor para o MinIO.",
                    e
            );
        }

        // Só depois do novo upload remove a foto antiga.
        String fotoAntiga = professor.getFoto();

        professor.setFoto(chaveObjeto);

        Professor atualizado =
                professorRepository.save(professor);

        if (fotoAntiga != null && !fotoAntiga.isBlank()) {
            removerArquivoSilenciosamente(fotoAntiga);
        }

        return professorMapper.toResponseDTO(atualizado);
    }

    // REMOVER FOTO DE PERFIL
    @Transactional
    public ProfessorResponseDTO removerFoto(Long professorId) {

        Professor professor =
                professorRepository.findById(professorId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Professor não encontrado: "
                                                + professorId
                                )
                        );

        String fotoAntiga = professor.getFoto();

        if (fotoAntiga != null && !fotoAntiga.isBlank()) {

            professor.setFoto(null);
            professorRepository.save(professor);

            removerArquivoSilenciosamente(fotoAntiga);
        }

        return professorMapper.toResponseDTO(professor);
    }

    // REMOVE UM ARQUIVO DO MINIO SEM INTERROMPER A OPERAÇÃO
    // CASO A FOTO ANTIGA JÁ NÃO EXISTA.
    private void removerArquivoSilenciosamente(
            String chaveObjeto
    ) {

        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketFotos)
                            .object(chaveObjeto)
                            .build()
            );

        } catch (Exception e) {
            System.err.println(
                    "Não foi possível remover a foto antiga do MinIO: "
                            + e.getMessage()
            );
        }
    }
}
