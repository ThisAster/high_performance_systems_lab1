package com.example.clinic.billing.integration.feign;

import com.example.clinic.billing.dto.AppointmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@FeignClient(name = "appointment-client", url = "${appointment.service.url}")
public interface AppointmentFeignClient {

    @GetMapping("/filter")
    List<AppointmentDto> findByPatients(List<Long> patientIds);
}
