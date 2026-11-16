package com.example.enaula.service;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

// Gera links temporários para exibir as fotos guardadas no MinIO.
// O bucket continua privado: só quem recebe o link (que expira) consegue ver a imagem.
@Slf4j
@Service
@RequiredArgsConstructor
public class UrlFotoService {

    // Mesma duração do token de login (1 hora).
    private static final int VALIDADE_EM_HORAS = 1;

    private final MinioClient minioClient;

    @Value("${minio.bucket.fotos}")
    private String bucketFotos;

    // Devolve o link da foto, ou null se não há foto ou se não foi possível gerar o link.
    // Falhar aqui não pode derrubar a resposta: sem link, a tela só mostra o ícone.
    public String gerarUrl(String chaveObjeto) {

        if (chaveObjeto == null || chaveObjeto.isBlank()) {
            return null;
        }

        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketFotos)
                            .object(chaveObjeto)
                            .expiry(VALIDADE_EM_HORAS, TimeUnit.HOURS)
                            .build()
            );
        } catch (Exception e) {
            log.warn("Não foi possível gerar o link da foto {}: {}",
                    chaveObjeto, e.getMessage());
            return null;
        }
    }
}