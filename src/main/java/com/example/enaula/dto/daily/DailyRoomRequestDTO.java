package com.example.enaula.dto.daily;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DailyRoomRequestDTO(DailyRoomProperties properties ) {

    public record DailyRoomProperties(@JsonProperty("enable_recording") String enablrRecording){

    }
}
