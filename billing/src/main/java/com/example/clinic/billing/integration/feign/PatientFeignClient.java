package com.example.clinic.billing.integration.feign;

import com.example.clinic.billing.dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@Profile("!test")
@FeignClient(name = "patient-client", url = "${patient.service.url}")
public interface PatientFeignClient {

    @GetMapping("/some")
    List<PatientDto> findByIds(@RequestParam List<Long> patients);
}
