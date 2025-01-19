package com.example.clinic.appointment.integration.feign;

import com.example.clinic.appointment.dto.DoctorDto;
import com.example.clinic.appointment.integration.DoctorService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "doctor-client", url = "${doctor.service.url}")
public interface DoctorClient extends DoctorService {


    @Override
    @GetMapping("/{id}")
    DoctorDto getDoctorById(@PathVariable Long id);
}
