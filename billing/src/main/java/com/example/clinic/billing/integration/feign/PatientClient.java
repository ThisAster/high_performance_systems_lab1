package com.example.clinic.billing.integration.feign;

import com.example.clinic.billing.dto.PatientDto;
import com.example.clinic.billing.integration.PatientService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PatientClient implements PatientService {
    @Override
    public Set<PatientDto> getPatientsWithAppointmentsByIds(Set<Long> patientIdSet) {
        return Set.of();
    }
}
