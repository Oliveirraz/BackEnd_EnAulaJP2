
package com.example.enaula.service;

import com.example.enaula.dto.AulaBuscaResponseDTO;
import com.example.enaula.entity.Aula;
import com.example.enaula.entity.Modalidade;
import com.example.enaula.repository.AulaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AulaBuscaService {

    private static final double RAIO_PRESENCIAL_KM = 5.0;
    private static final double RAIO_ONLINE_KM = 100.0;

    private final AulaRepository aulaRepository;

    @Transactional(readOnly = true)
    public List<AulaBuscaResponseDTO> buscar(
            Long materiaId,
            Long professorId,
            BigDecimal valorMinimo,
            BigDecimal valorMaximo,
            Modalidade modalidade,
            Double latitude,
            Double longitude,
            Double raioKm
    ) {

        validarFiltros(
                valorMinimo,
                valorMaximo,
                latitude,
                longitude,
                raioKm
        );

        if (latitude == null || longitude == null) {
            throw new IllegalArgumentException(
                    "Cadastre sua localização no perfil antes de buscar aulas."
            );
        }

        LocalDate hoje = LocalDate.now();
        LocalTime agora = LocalTime.now();

        return aulaRepository.buscarAulasFuturas(hoje)
                .stream()

                // Somente aulas que ainda não começaram
                .filter(aula ->
                        aula.getData().isAfter(hoje)
                                ||
                                (aula.getData().isEqual(hoje)
                                        &&
                                        aula.getHorario().isAfter(agora))
                )

                // Filtro por matéria
                .filter(aula ->
                        materiaId == null
                                ||
                                aula.getMateria().getId().equals(materiaId)
                )

                // Filtro por professor
                .filter(aula ->
                        professorId == null
                                ||
                                aula.getProfessor().getId().equals(professorId)
                )

                // Filtro por modalidade
                .filter(aula ->
                        modalidade == null
                                ||
                                aula.getModalidade() == modalidade
                )

                // Montagem dos resultados e cálculo da distância
                .map(aula ->
                        montarResultado(
                                aula,
                                latitude,
                                longitude,
                                raioKm
                        )
                )

                .filter(resultado -> resultado != null)

                // Filtro por valor mínimo
                .filter(resultado ->
                        valorMinimo == null
                                ||
                                resultado.valorHoraAula()
                                        .compareTo(valorMinimo) >= 0
                )

                // Filtro por valor máximo
                .filter(resultado ->
                        valorMaximo == null
                                ||
                                resultado.valorHoraAula()
                                        .compareTo(valorMaximo) <= 0
                )

                // Ordenação pela menor distância
                .sorted(
                        Comparator.comparing(
                                AulaBuscaResponseDTO::distanciaKm,
                                Comparator.nullsLast(Double::compareTo)
                        )
                )

                .toList();
    }

    private AulaBuscaResponseDTO montarResultado(
            Aula aula,
            Double latitude,
            Double longitude,
            Double raioInformado
    ) {

        // Localização do professor
        Double latProfessor = aula.getProfessor().getLatitude();
        Double lonProfessor = aula.getProfessor().getLongitude();

        if (latProfessor == null || lonProfessor == null) {
            return null;
        }

        // Raio padrão conforme modalidade
        double raioPadrao =
                aula.getModalidade() == Modalidade.PRESENCIAL
                        ? RAIO_PRESENCIAL_KM
                        : RAIO_ONLINE_KM;

        double raioAplicado =
                raioInformado == null
                        ? raioPadrao
                        : raioInformado;

        // Cálculo da distância
        double distancia = calcularDistanciaKm(
                latitude,
                longitude,
                latProfessor,
                lonProfessor
        );

        if (distancia > raioAplicado) {
            return null;
        }

        // Validação dos dados da aula
        if (aula.getValorAula() == null
                || aula.getDuracao() == null
                || aula.getDuracao() <= 0) {

            return null;
        }

        // CORREÇÃO:
        // O valor cadastrado já representa o preço POR HORA.
        // Portanto, não devemos multiplicar por 60
        // nem dividir pela duração da aula.

        BigDecimal valorHora = aula.getValorAula()
                .setScale(2, RoundingMode.HALF_UP);

        return new AulaBuscaResponseDTO(
                aula.getId(),
                aula.getProfessor().getId(),
                aula.getProfessor().getNome(),
                aula.getMateria().getId(),
                aula.getMateria().getNome(),
                valorHora,
                null,
                distancia,
                aula.getModalidade(),
                aula.getData(),
                aula.getHorario(),
                aula.getDuracao()
        );
    }

    // Fórmula de Haversine
    private double calcularDistanciaKm(
            double lat1,
            double lon1,
            double lat2,
            double lon2
    ) {

        double raioTerraKm = 6371.0;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a =
                Math.pow(Math.sin(dLat / 2), 2)
                        +
                        Math.cos(Math.toRadians(lat1))
                                *
                                Math.cos(Math.toRadians(lat2))
                                *
                                Math.pow(Math.sin(dLon / 2), 2);

        return 2 * raioTerraKm
                * Math.atan2(
                Math.sqrt(a),
                Math.sqrt(1 - a)
        );
    }

    private void validarFiltros(
            BigDecimal minimo,
            BigDecimal maximo,
            Double latitude,
            Double longitude,
            Double raioKm
    ) {

        if ((minimo != null && minimo.signum() < 0)
                ||
                (maximo != null && maximo.signum() < 0)) {

            throw new IllegalArgumentException(
                    "Os valores devem ser positivos ou zero."
            );
        }

        if (minimo != null
                && maximo != null
                && minimo.compareTo(maximo) > 0) {

            throw new IllegalArgumentException(
                    "O valor mínimo não pode ser maior que o máximo."
            );
        }

        // Latitude e longitude devem ser informadas juntas
        if ((latitude == null) != (longitude == null)) {

            throw new IllegalArgumentException(
                    "Informe latitude e longitude juntas."
            );
        }

        // Validação das coordenadas
        if (latitude != null
                &&
                (latitude < -90 || latitude > 90
                        ||
                        longitude < -180 || longitude > 180)) {

            throw new IllegalArgumentException(
                    "Coordenadas geográficas inválidas."
            );
        }

        if (raioKm != null && raioKm <= 0) {

            throw new IllegalArgumentException(
                    "O raio deve ser maior que zero."
            );
        }

        if (raioKm != null && latitude == null) {

            throw new IllegalArgumentException(
                    "Informe latitude e longitude para utilizar o raio."
            );
        }
    }
}
