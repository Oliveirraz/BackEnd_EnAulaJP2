package com.example.enaula.service;

import com.example.enaula.dto.MateriaRequestDTO;
import com.example.enaula.dto.MateriaResponseDTO;
import com.example.enaula.entity.Materia;
import com.example.enaula.entity.Professor;
import com.example.enaula.mapper.MateriaMapper;
import com.example.enaula.repository.MateriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MateriaService {

    private final MateriaRepository materiaRepository;
    private final MateriaMapper materiaMapper;

    // Cadastrar a Materia
    public MateriaResponseDTO cadastrarMateria(
            MateriaRequestDTO dto,
            Professor professor
    ){
        Materia materia = materiaMapper.toEntity(dto, professor);

        Materia materiaSalva = materiaRepository.save(materia);

        return materiaMapper.toResponseDTO(materiaSalva);
    }

    //Listar as matérias do Professor
    public List<MateriaResponseDTO> listarMaterias(Professor professor){
        return materiaRepository.findAllByProfessor(professor)
                .stream()
                .map(materiaMapper::toResponseDTO)
                .toList();
    }

    // Buscar por Id
    public MateriaResponseDTO buscarPorId(Long id, Professor professor){
        Materia materia = buscarEValidarPosse(id, professor);

        return materiaMapper.toResponseDTO(materia);
    }

    // Atualizar à Matéria
    public MateriaResponseDTO atualizarMateria(
            Long id,
            MateriaRequestDTO dto,
            Professor professor
    ){
        Materia materia = buscarEValidarPosse(id, professor);

        materiaMapper.updadeEntity(materia, dto);

        Materia materiaAtualizada = materiaRepository.save(materia);

        return materiaMapper.toResponseDTO(materiaAtualizada);
    }

    //Deletar à Matéria
    public void deletarMateria(Long id, Professor professor){
        Materia materia = buscarEValidarPosse(id, professor);

        materiaRepository.delete(materia);
    }

    //Buscar e validar a posse da matéria
    private Materia buscarEValidarPosse(long id, Professor professor){
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matéria não encontrada"));
        if (!materia.getProfessor().getId().equals(professor.getId())){
            throw new AccessDeniedException(
                    "Você não tem peermissão para acessar essa matéria"
            );
        }
        return materia;
    }

}
