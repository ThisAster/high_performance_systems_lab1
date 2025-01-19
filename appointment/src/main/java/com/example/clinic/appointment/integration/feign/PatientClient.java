package com.example.clinic.appointment.integration.feign;

import com.example.clinic.appointment.dto.PatientDto;
import com.example.clinic.appointment.integration.PatientService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
@FeignClient(name = "patient-client", url = "${patient.service.url}")
public interface PatientClient extends PatientService {

    @Override
    @GetMapping("/{id}")
    Optional<PatientDto> findById(@PathVariable Long id);
}
