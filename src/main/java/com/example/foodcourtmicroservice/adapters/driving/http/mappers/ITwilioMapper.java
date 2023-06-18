package com.example.foodcourtmicroservice.adapters.driving.http.mappers;



import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.SmsMessageRequestDto;
        import com.example.foodcourtmicroservice.domain.model.SmsMessageModel;
        import org.mapstruct.Mapper;
        import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ITwilioMapper {
    SmsMessageRequestDto toSmsRequest(SmsMessageModel smsMessageModel);
}