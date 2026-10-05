package com.example.enaula.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "material_apoio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaterialApoio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aula_id", nullable = false)
    private Aula aula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @Column(name = "nome_original", nullable = false)
    private String nomeOriginal; //Nome do arquivo como o usuário enviou

    @Column(name = "chave_objeto", nullable = false, unique = true)
    private String chaveObjeto; //Object key dentro do bucket MinIO

    @Column(name = "nome_bucket", nullable = false)
    private String nomeBucket;

    @Column(name = "tipo_conteudo", nullable = false)
    private String tipoConteudo;

    @Column(name = "tamanho_bytes", nullable = false)
    private Long tamanhoBytes;

    @Column(name = "data_envio", nullable = false)
    private LocalDateTime dataEnvio;
}
