package com.example.clinic.appointment.integration.feign;

import com.example.clinic.appointment.dto.EmailDto;
import com.example.clinic.appointment.integration.EmailService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "email-client", url = "${mail.service.url}")
public interface EmailClient extends EmailService {
    @Override
    @PostMapping("/send")
    void sendEmail(@RequestBody EmailDto dto);
}
