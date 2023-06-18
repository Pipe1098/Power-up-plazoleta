package com.example.foodcourtmicroservice.adapters.driving.http.feigns;

import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.LogsRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.dto.response.LogsResponseDto;
import com.example.foodcourtmicroservice.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "trazability-microservice", url = "${external.trazabilitymicroservice.base-url}",configuration = FeignClientConfig.class)
public interface TrazabilityFeignClient {
    @PostMapping("/api/v1/log")
    void createLog(@RequestBody LogsRequestDto logDto);

    @GetMapping("/api/v1/log/{idOrder}")
     List<LogsResponseDto> getLogOrder(@PathVariable("idOrder") Long idOrder);

    @GetMapping("/api/v1/time/{idOrder}")
    Long getTimeOrder(@PathVariable("idOrder") Long idOrder);
}