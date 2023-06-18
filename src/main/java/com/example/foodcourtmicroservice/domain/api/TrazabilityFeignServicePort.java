package com.example.foodcourtmicroservice.domain.api;


import com.example.foodcourtmicroservice.adapters.driving.http.dto.response.LogsResponseDto;
import com.example.foodcourtmicroservice.domain.model.LogModel;

import java.util.List;


public interface TrazabilityFeignServicePort {
    void saveLog(LogModel log);
    List<LogsResponseDto> timeStates(Long idOrder);
    Long totalTime(Long id);
}
