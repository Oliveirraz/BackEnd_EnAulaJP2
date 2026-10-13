package com.example.enaula.service;

import com.example.enaula.dto.daily.DailyRoomRequestDTO;
import com.example.enaula.dto.daily.DailyRoomResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class DailyService {

    private final RestClient dailyRestClient;

    public DailyRoomResponseDTO criarSalaComGravacao() {

        DailyRoomRequestDTO requestBody = new DailyRoomRequestDTO(
                new DailyRoomRequestDTO.DailyRoomProperties("local")
        );

        return dailyRestClient.post()
                .uri("/rooms")
                .body(requestBody)
                .retrieve()
                .body(DailyRoomResponseDTO.class);
    }
}
