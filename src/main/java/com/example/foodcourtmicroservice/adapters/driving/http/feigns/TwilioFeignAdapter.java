package com.example.foodcourtmicroservice.adapters.driving.http.feigns;

import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.SmsMessageRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.feigns.TwilioFeignClients;
import com.example.foodcourtmicroservice.adapters.driving.http.mappers.ITwilioMapper;
import com.example.foodcourtmicroservice.domain.api.ITwilioFeignServicePort;
import com.example.foodcourtmicroservice.domain.model.SmsMessageModel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TwilioFeignAdapter implements ITwilioFeignServicePort {

    private final TwilioFeignClients twilioFeignClients;
    private final ITwilioMapper twilioMapper;
    @Override
    public void sendSmsMessage(SmsMessageModel smsMessageModel) {
        SmsMessageRequestDto smsMessageRequestDto = twilioMapper.toSmsRequest(smsMessageModel);
        twilioFeignClients.sendSmsMessage(smsMessageRequestDto);
    }
}
