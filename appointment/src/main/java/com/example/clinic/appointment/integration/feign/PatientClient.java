package com.example.clinic.appointment.integration.feign;

import com.example.clinic.appointment.dto.PatientDto;
import com.example.clinic.appointment.integration.PatientService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.of;

@Service
public class PatientClient implements PatientService {
    @Override
    public Optional<PatientDto> findById(Long id) {
        //TODO: replace with feign client
        return of(new PatientDto("test@email.ru", "Name"));
    }
}
