package com.example.foodcourtmicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogsResponseDto {


    private Long idOrder;
    private Long duration1;
    private Long duration2;
    private Long duration3;
    private Long totalDuration;

    @Override
    public String toString() {
        return "LogsResponseDto{" +
                "\n  idOrder=" + idOrder +
                "\n  Pending_to_InPreparation=" + duration1 + " minutes" +
                "\n  InPreparation_to_Ready=" + duration2 + " minutes" +
                "\n  Ready_to_Delivered=" + duration3 + " minutes" +
                "\n  total waiting time=" + totalDuration + " minutes" +
                "\n}";
    }
}
