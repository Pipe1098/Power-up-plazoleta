package com.example.foodcourtmicroservice.adapters.driving.http.feigns;

import com.example.foodcourtmicroservice.adapters.driving.http.dto.response.LogsResponseDto;
import com.example.foodcourtmicroservice.adapters.driving.http.mappers.ITrazabilityMapper;
import com.example.foodcourtmicroservice.domain.api.TrazabilityFeignServicePort;
import com.example.foodcourtmicroservice.domain.model.LogModel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TrazabilityFeignAdapter implements TrazabilityFeignServicePort {

    private final TrazabilityFeignClient trazabilityFeignClient;
    private final ITrazabilityMapper ITrazabilityMapper;

    @Override
    public void saveLog(LogModel log) {
        trazabilityFeignClient.createLog(ITrazabilityMapper.toLogDto(log));
    }

    @Override
    public List<LogsResponseDto> timeStates(Long idOrder) {
        return trazabilityFeignClient.getLogOrder(idOrder);
    }

}
