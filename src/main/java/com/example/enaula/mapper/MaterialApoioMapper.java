package com.example.enaula.mapper;

import com.example.enaula.dto.MaterialApoioResponseDTO;
import com.example.enaula.entity.MaterialApoio;
import org.springframework.stereotype.Component;

@Component
public class MaterialApoioMapper {

    public MaterialApoioResponseDTO toResponseDTO(MaterialApoio material) {

        return new MaterialApoioResponseDTO(
                material.getId(),
                material.getNomeOriginal(),
                material.getTipoConteudo(),
                material.getTamanhoBytes(),
                material.getDataEnvio(),
                material.getAula().getId(),
                material.getProfessor().getId(),
                material.getProfessor().getNome()
        );
    }
}
