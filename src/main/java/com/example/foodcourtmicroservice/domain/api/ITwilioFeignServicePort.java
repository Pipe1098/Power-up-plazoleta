package com.example.foodcourtmicroservice.domain.api;

import com.example.foodcourtmicroservice.domain.model.SmsMessageModel;

public interface ITwilioFeignServicePort {
    void sendSmsMessage(SmsMessageModel smsMessageModel);
}
