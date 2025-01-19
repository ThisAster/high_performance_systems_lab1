package com.example.clinic.appointment.integration.feign;

import com.example.clinic.appointment.dto.DoctorDto;
import com.example.clinic.appointment.integration.DoctorService;
import org.springframework.stereotype.Service;

@Service
public class DoctorClient implements DoctorService {
    @Override
    public DoctorDto getDoctorById(Long doctor) {
        return new DoctorDto(2L, "Super doc", "Super spec");
    }
}
