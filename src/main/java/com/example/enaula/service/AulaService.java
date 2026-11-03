package com.example.enaula.service;

import com.example.enaula.dto.AulaRequestDTO;
import com.example.enaula.dto.AulaResponseDTO;
import com.example.enaula.dto.daily.DailyRoomResponseDTO;
import com.example.enaula.entity.*;
import com.example.enaula.exception.ResourceNotFoundException;
import com.example.enaula.mapper.AulaMapper;
import com.example.enaula.repository.AulaRepository;
import com.example.enaula.repository.MateriaRepository;
import com.example.enaula.repository.MatriculaRepository;
import com.example.enaula.repository.TabelaPrecoRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.GetObjectArgs;

import jakarta.servlet.http.HttpServletResponse;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AulaService {

    private final AulaRepository aulaRepository;
    private final MateriaRepository materiaRepository;
    private final TabelaPrecoRepository tabelaPrecoRepository;
    private final AulaMapper aulaMapper;
    private final DailyService dailyService;
    private final MatriculaRepository matriculaRepository;
    private final EmailService emailService;
    private final MinioClient minioClient;

    @Value("${minio.bucket.gravacoes}")
    private String bucketGravacoes;

    // HU024: prazo de retenção das gravações.
    @Value("${enaula.gravacao.retencao-dias:30}")
    private long diasRetencaoGravacao;

    private static final List<String> TIPOS_VIDEO_PERMITIDOS = List.of(
            "video/webm",
            "video/mp4"
    );

    private static final long TAMANHO_MAXIMO_GRAVACAO =
            500 * 1024 * 1024; // 500MB


    // =====================================================
    // CADASTRAR AULA
    // =====================================================

    public AulaResponseDTO cadastrarAula(
            AulaRequestDTO dto,
            Professor professor
    ) {

        Materia materia = materiaRepository.findById(dto.materiaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Matéria não encontrada"
                ));

        validarMateriaDoProfessor(materia, professor);
        validarHorarioSemConflito(dto, professor, null);
        validarFormatoEParticipantes(dto);
        validarValorMinimo(dto.valorAula(), professor);

        Aula aula = aulaMapper.toEntity(dto, materia, professor);

        return aulaMapper.toResponseDTO(
                aulaRepository.save(aula)
        );
    }


    // =====================================================
    // LISTAR AULAS
    // =====================================================

    public List<AulaResponseDTO> listarAulas(Professor professor) {

        return aulaRepository.findAllByProfessor(professor)
                .stream()
                .map(aulaMapper::toResponseDTO)
                .toList();
    }


    // =====================================================
    // BUSCAR AULA POR ID
    // =====================================================

    public AulaResponseDTO buscarPorId(
            Long id,
            Professor professor
    ) {

        return aulaMapper.toResponseDTO(
                buscarEValidarPosse(id, professor)
        );
    }


    // =====================================================
    // ATUALIZAR AULA
    // =====================================================

    public AulaResponseDTO atualizarAula(
            Long id,
            AulaRequestDTO dto,
            Professor professor
    ) {

        Aula aula = buscarEValidarPosse(id, professor);

        Materia materia = materiaRepository.findById(dto.materiaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Matéria não encontrada"
                ));

        validarMateriaDoProfessor(materia, professor);
        validarHorarioSemConflito(dto, professor, id);
        validarFormatoEParticipantes(dto);
        validarValorMinimo(dto.valorAula(), professor);

        aula.setMateria(materia);
        aula.setData(dto.data());
        aula.setHorario(dto.horario());
        aula.setDuracao(dto.duracao());
        aula.setModalidade(dto.modalidade());
        aula.setFormato(dto.formato());
        aula.setValorAula(dto.valorAula());

        aula.setQuantidadeParticipantes(
                dto.quantidadeParticipantes()
        );

        return aulaMapper.toResponseDTO(
                aulaRepository.save(aula)
        );
    }


    // =====================================================
    // DELETAR AULA
    // =====================================================

    public void deletarAula(
            Long id,
            Professor professor
    ) {

        aulaRepository.delete(
                buscarEValidarPosse(id, professor)
        );
    }


    // =====================================================
    // VALIDAR CONFLITO DE HORÁRIO
    // =====================================================

    private void validarHorarioSemConflito(
            AulaRequestDTO dto,
            Professor professor,
            Long idIgnorado
    ) {

        if (dto.data() == null || dto.horario() == null) {
            throw new IllegalArgumentException(
                    "A data e o horário da aula são obrigatórios"
            );
        }

        if (dto.duracao() == null || dto.duracao() < 1) {
            throw new IllegalArgumentException(
                    "A duração mínima da aula é de 1 hora"
            );
        }

        LocalTime horarioInicio = dto.horario();

        LocalTime horarioFim =
                horarioInicio.plusHours(dto.duracao());

        if (!horarioFim.isAfter(horarioInicio)) {
            throw new IllegalArgumentException(
                    "A duração da aula ultrapassa o limite de um dia"
            );
        }

        List<Aula> aulasDoDia =
                aulaRepository.findAllByProfessorAndData(
                        professor,
                        dto.data()
                );

        boolean existeConflito = aulasDoDia.stream()
                .filter(aula -> idIgnorado == null
                        || !aula.getId().equals(idIgnorado))
                .anyMatch(aula -> {

                    LocalTime inicioExistente =
                            aula.getHorario();

                    LocalTime fimExistente =
                            inicioExistente.plusHours(
                                    aula.getDuracao()
                            );

                    return horarioInicio.isBefore(fimExistente)
                            && inicioExistente.isBefore(horarioFim);
                });

        if (existeConflito) {
            throw new IllegalArgumentException(
                    "O professor já possui uma aula nesse período. "
                            + "Escolha outro horário para esta aula."
            );
        }
    }


    // =====================================================
    // VALIDAR FORMATO E PARTICIPANTES
    // =====================================================

    private void validarFormatoEParticipantes(
            AulaRequestDTO dto
    ) {

        Integer participantes =
                dto.quantidadeParticipantes();

        if (participantes == null) {
            throw new IllegalArgumentException(
                    "A quantidade de participantes deve ser informada"
            );
        }

        if (dto.formato() == FormatoAula.INDIVIDUAL
                && participantes != 1) {

            throw new IllegalArgumentException(
                    "Aula individual deve ter exatamente 1 participante"
            );
        }

        if (dto.formato() == FormatoAula.GRUPO
                && participantes < 2) {

            throw new IllegalArgumentException(
                    "Aula em grupo deve ter pelo menos 2 participantes"
            );
        }
    }


    // =====================================================
    // VALIDAR VALOR MÍNIMO
    // =====================================================

    private void validarValorMinimo(
            BigDecimal valorAula,
            Professor professor
    ) {

        if (professor.getTitulacao() == null) {
            throw new IllegalArgumentException(
                    "O professor precisa possuir uma titulação cadastrada "
                            + "para validar o valor mínimo da aula"
            );
        }

        TabelaPreco tabelaPreco = tabelaPrecoRepository
                .findByTitulacao(professor.getTitulacao())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Não existe valor mínimo configurado para a titulação "
                                + professor.getTitulacao()
                ));

        if (valorAula.compareTo(
                tabelaPreco.getValorMinimo()) < 0) {

            throw new IllegalArgumentException(
                    "O valor da aula não pode ser inferior ao mínimo de R$ "
                            + tabelaPreco.getValorMinimo()
                            + " para a titulação "
                            + professor.getTitulacao()
            );
        }
    }


    // =====================================================
    // BUSCAR E VALIDAR POSSE DA AULA
    // =====================================================

    private Aula buscarEValidarPosse(
            Long id,
            Professor professor
    ) {

        Aula aula = aulaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Aula não encontrada"
                ));

        if (aula.getProfessor() == null
                || !aula.getProfessor().getId()
                .equals(professor.getId())) {

            throw new AccessDeniedException(
                    "Você não tem permissão para acessar essa aula"
            );
        }

        return aula;
    }


    // =====================================================
    // VALIDAR MATÉRIA DO PROFESSOR
    // =====================================================

    private void validarMateriaDoProfessor(
            Materia materia,
            Professor professor
    ) {

        if (materia.getProfessor() == null
                || !materia.getProfessor().getId()
                .equals(professor.getId())) {

            throw new AccessDeniedException(
                    "Você não tem permissão para utilizar essa matéria"
            );
        }
    }


    // =====================================================
    // INICIAR AULA ONLINE
    // =====================================================

    public AulaResponseDTO iniciarAula(
            Long id,
            Professor professor
    ) {

        Aula aula = buscarEValidarPosse(id, professor);

        if (aula.getModalidade() != Modalidade.ONLINE) {
            throw new IllegalArgumentException(
                    "Só é possível iniciar sala de vídeo para aulas "
                            + "com modalidade ONLINE"
            );
        }

        if (aula.getSalaUrl() == null
                || aula.getSalaUrl().isBlank()) {

            DailyRoomResponseDTO sala =
                    dailyService.criarSalaComGravacao();

            aula.setSalaUrl(sala.url());
            aula.setSalaNome(sala.name());

            aula = aulaRepository.save(aula);
        }

        String linkAula = aula.getSalaUrl();

        if (linkAula == null || linkAula.isBlank()) {
            throw new IllegalStateException(
                    "Não foi possível obter o link da sala virtual."
            );
        }

        List<Matricula> matriculasAceitas =
                matriculaRepository
                        .findAllByAulaAndStatusOrderByDataSolicitacaoAsc(
                                aula,
                                StatusMatricula.ACEITA
                        );

        System.out.println(
                "Aula " + aula.getId()
                        + " - matrículas aceitas: "
                        + matriculasAceitas.size()
        );

        for (Matricula matricula : matriculasAceitas) {

            Aluno aluno = matricula.getAluno();

            if (aluno == null
                    || aluno.getEmail() == null
                    || aluno.getEmail().isBlank()) {

                System.out.println(
                        "Matrícula sem aluno ou e-mail válido."
                );

                continue;
            }

            String nomeMateria = aula.getMateria().getNome();

            String assunto =
                    "Link da aula online - " + nomeMateria;

            String mensagem =
                    "Olá, " + aluno.getNome() + "!\n\n"
                            + "Sua matrícula foi aceita e a aula online "
                            + "foi iniciada.\n\n"
                            + "Matéria: " + nomeMateria + "\n"
                            + "Data: " + aula.getData() + "\n"
                            + "Horário: " + aula.getHorario() + "\n\n"
                            + "Acesse a sala virtual pelo link abaixo:\n"
                            + linkAula + "\n\n"
                            + "Até a aula!\n"
                            + "Equipe EnAula";

            System.out.println(
                    "Solicitando envio do link para: "
                            + aluno.getEmail()
            );

            emailService.enviarEmail(
                    aluno.getEmail(),
                    assunto,
                    mensagem
            );
        }

        return aulaMapper.toResponseDTO(aula);
    }


    // =====================================================
    // ENVIAR GRAVAÇÃO PARA O MINIO
    // =====================================================

    @Transactional
    public AulaResponseDTO enviarGravacao(
            Long id,
            Professor professor,
            MultipartFile arquivo
    ) {

        Aula aula = buscarEValidarPosse(id, professor);

        if (arquivo == null || arquivo.isEmpty()) {
            throw new IllegalArgumentException(
                    "Arquivo vazio."
            );
        }

        String tipoConteudo = arquivo.getContentType();

        if (tipoConteudo == null
                || !TIPOS_VIDEO_PERMITIDOS.contains(tipoConteudo)) {

            throw new IllegalArgumentException(
                    "Tipo de vídeo não permitido: " + tipoConteudo
            );
        }

        if (arquivo.getSize() > TAMANHO_MAXIMO_GRAVACAO) {
            throw new IllegalArgumentException(
                    "Vídeo muito grande. Tamanho máximo: 500MB."
            );
        }

        if (aula.getGravacaoChaveObjeto() != null) {
            removerGravacaoSilenciosamente(
                    aula.getGravacaoChaveObjeto()
            );
        }

        String nomeOriginal = arquivo.getOriginalFilename();

        if (nomeOriginal == null || nomeOriginal.isBlank()) {
            nomeOriginal = "gravacao-aula-" + id + ".mp4";
        }

        String chaveObjeto =
                "aulas/" + id + "/"
                        + UUID.randomUUID() + "_"
                        + nomeOriginal;

        try {

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketGravacoes)
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
                    "Falha ao enviar gravação para o MinIO.",
                    e
            );
        }

        aula.setGravacaoChaveObjeto(chaveObjeto);
        aula.setGravacaoNomeOriginal(nomeOriginal);
        aula.setGravacaoDataEnvio(LocalDateTime.now());

        return aulaMapper.toResponseDTO(
                aulaRepository.save(aula)
        );
    }


    // =====================================================
    // REMOVER GRAVAÇÃO ANTIGA DO MINIO
    // =====================================================

    private void removerGravacaoSilenciosamente(
            String chaveObjeto
    ) {

        try {

            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketGravacoes)
                            .object(chaveObjeto)
                            .build()
            );

        } catch (Exception e) {

            // Não interrompe o envio de uma nova gravação
            // caso a gravação antiga não possa ser removida.
        }
    }


    // =====================================================
    // HU024 - BAIXAR GRAVAÇÃO
    // =====================================================

    public void baixarGravacao(
            Long id,
            Object principal,
            HttpServletResponse response
    ) {

        Aula aula = aulaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Aula não encontrada"
                ));

        // Primeiro verifica a autorização do usuário.
        validarAcessoGravacao(aula, principal);

        if (aula.getGravacaoChaveObjeto() == null
                || aula.getGravacaoChaveObjeto().isBlank()) {

            throw new ResourceNotFoundException(
                    "Essa aula ainda não possui gravação disponível."
            );
        }

        // HU024: verifica o prazo de retenção.
        validarPrazoRetencaoGravacao(aula);

        String nomeArquivo = aula.getGravacaoNomeOriginal();

        if (nomeArquivo == null || nomeArquivo.isBlank()) {
            nomeArquivo = "gravacao-aula-" + aula.getId() + ".mp4";
        }

        // Evita caracteres que possam comprometer o cabeçalho.
        nomeArquivo = nomeArquivo
                .replace("\"", "")
                .replace("\r", "")
                .replace("\n", "");

        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketGravacoes)
                        .object(aula.getGravacaoChaveObjeto())
                        .build()
        )) {

            response.setContentType("video/mp4");

            response.setHeader(
                    "Content-Disposition",
                    "attachment; filename=\"" + nomeArquivo + "\""
            );

            stream.transferTo(response.getOutputStream());

            response.flushBuffer();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Falha ao baixar gravação do MinIO.",
                    e
            );
        }
    }


    // =====================================================
    // HU024 - VALIDAR PRAZO DE RETENÇÃO
    // =====================================================

    private void validarPrazoRetencaoGravacao(Aula aula) {

        // Prioriza a data real de envio da gravação.
        LocalDateTime inicioRetencao =
                aula.getGravacaoDataEnvio();

        // Para gravações antigas sem data de envio,
        // utiliza o término previsto da aula.
        if (inicioRetencao == null) {

            inicioRetencao = LocalDateTime.of(
                    aula.getData(),
                    aula.getHorario()
            ).plusHours(aula.getDuracao());
        }

        LocalDateTime limiteRetencao =
                inicioRetencao.plusDays(diasRetencaoGravacao);

        if (LocalDateTime.now().isAfter(limiteRetencao)) {

            throw new AccessDeniedException(
                    "O prazo de retenção desta gravação expirou."
            );
        }
    }


    // =====================================================
    // HU024 - VALIDAR ACESSO À GRAVAÇÃO
    // =====================================================

    private void validarAcessoGravacao(
            Aula aula,
            Object principal
    ) {

        // ---------------------------------------------
        // ACESSO DO PROFESSOR
        // ---------------------------------------------

        if (principal instanceof Professor professor) {

            if (aula.getProfessor() != null
                    && aula.getProfessor().getId()
                    .equals(professor.getId())) {

                return;
            }

            throw new AccessDeniedException(
                    "Você não tem permissão para acessar essa gravação."
            );
        }


        // ---------------------------------------------
        // ACESSO DO ALUNO
        // ---------------------------------------------

        if (principal instanceof Aluno aluno) {

            boolean matriculado = matriculaRepository
                    .findAllByAulaAndStatusOrderByDataSolicitacaoAsc(
                            aula,
                            StatusMatricula.ACEITA
                    )
                    .stream()
                    .anyMatch(m ->
                            m.getAluno() != null
                                    && m.getAluno().getId()
                                    .equals(aluno.getId())
                    );

            if (!matriculado) {

                throw new AccessDeniedException(
                        "Você não tem permissão para acessar essa gravação."
                );
            }

            // Calcula o término previsto da aula.
            LocalDateTime fimAula = LocalDateTime.of(
                    aula.getData(),
                    aula.getHorario()
            ).plusHours(aula.getDuracao());

            // Impede acesso antes do término da aula.
            if (LocalDateTime.now().isBefore(fimAula)) {

                throw new AccessDeniedException(
                        "A gravação só poderá ser acessada após "
                                + "o término da aula."
                );
            }

            return;
        }


        // ---------------------------------------------
        // USUÁRIO NÃO AUTORIZADO
        // ---------------------------------------------

        throw new AccessDeniedException(
                "Usuário não autorizado."
        );
    }
}