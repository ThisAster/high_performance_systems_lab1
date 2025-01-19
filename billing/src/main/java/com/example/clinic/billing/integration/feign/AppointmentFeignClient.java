package com.example.clinic.billing.integration.feign;

import com.example.clinic.billing.dto.AppointmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@Profile("!test")
@FeignClient(name = "appointment-client", url = "${appointment.service.url}")
public interface AppointmentFeignClient {

    @GetMapping("/filter")
    List<AppointmentDto> findByPatients(@RequestParam List<Long> patients);
}
