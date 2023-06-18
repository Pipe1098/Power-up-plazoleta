package com.example.foodcourtmicroservice.adapters.driving.http.mappers;

import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.LogsRequestDto;
import com.example.foodcourtmicroservice.domain.model.LogModel;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy =  ReportingPolicy.IGNORE)

public interface ITrazabilityMapper {
    LogModel toLog(LogsRequestDto logDto);
    LogsRequestDto toLogDto(LogModel logModel);
}