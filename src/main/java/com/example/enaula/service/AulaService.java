package com.example.enaula.service;

import com.example.enaula.dto.AulaRequestDTO;
import com.example.enaula.dto.AulaResponseDTO;
import com.example.enaula.entity.Aula;
import com.example.enaula.entity.Materia;
import com.example.enaula.entity.Professor;
import com.example.enaula.mapper.AulaMapper;
import com.example.enaula.repository.AulaRepository;
import com.example.enaula.repository.MateriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AulaService {

    private final AulaRepository aulaRepository;
    private final MateriaRepository materiaRepository;
    private final AulaMapper aulaMapper;

    // CADASTRAR
    public AulaResponseDTO cadastrarAula(
            AulaRequestDTO dto,
            Professor professor
    ) {
        Materia materia = materiaRepository.findById(dto.materiaId())
                .orElseThrow(() ->
                        new RuntimeException("Matéria não encontrada"));

        validarMateriaDoProfessor(materia, professor);

        Aula aula = aulaMapper.toEntity(dto, materia, professor);

        Aula aulaSalva = aulaRepository.save(aula);

        return aulaMapper.toResponseDTO(aulaSalva);
    }

    // LISTAR
    public List<AulaResponseDTO> listarAulas(
            Professor professor
    ) {
        return aulaRepository.findAllByProfessor(professor)
                .stream()
                .map(aulaMapper::toResponseDTO)
                .toList();
    }

    // BUSCAR POR ID
    public AulaResponseDTO buscarPorId(
            Long id,
            Professor professor
    ) {
        Aula aula = buscarEValidarPosse(id, professor);

        return aulaMapper.toResponseDTO(aula);
    }

    // ATUALIZAR
    public AulaResponseDTO atualizarAula(
            Long id,
            AulaRequestDTO dto,
            Professor professor
    ) {
        Aula aula = buscarEValidarPosse(id, professor);

        Materia materia = materiaRepository.findById(dto.materiaId())
                .orElseThrow(() ->
                        new RuntimeException("Matéria não encontrada"));

        validarMateriaDoProfessor(materia, professor);

        aula.setMateria(materia);
        aula.setData(dto.data());
        aula.setHorario(dto.horario());
        aula.setDuracao(dto.duracao());
        aula.setModalidade(dto.modalidade());

        Aula aulaAtualizada = aulaRepository.save(aula);

        return aulaMapper.toResponseDTO(aulaAtualizada);
    }

    // DELETAR
    public void deletarAula(
            Long id,
            Professor professor
    ) {
        Aula aula = buscarEValidarPosse(id, professor);

        aulaRepository.delete(aula);
    }

    // Verifica se a aula pertence ao professor
    private Aula buscarEValidarPosse(
            Long id,
            Professor professor
    ) {
        Aula aula = aulaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Aula não encontrada"));

        if (!aula.getProfessor().getId().equals(professor.getId())) {
            throw new AccessDeniedException(
                    "Você não tem permissão para acessar essa aula"
            );
        }

        return aula;
    }

    // Verifica se a matéria pertence ao professor
    private void validarMateriaDoProfessor(
            Materia materia,
            Professor professor
    ) {
        if (!materia.getProfessor().getId().equals(professor.getId())) {
            throw new AccessDeniedException(
                    "Você não tem permissão para utilizar essa matéria"
            );
        }
    }
}