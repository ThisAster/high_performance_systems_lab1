package com.example.clinic.billing.integration.feign;

import com.example.clinic.billing.dto.PatientDto;
import com.example.clinic.billing.integration.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PatientClient implements PatientService {
    @Override
    public List<PatientDto> getPatientsWithAppointmentsByIds(Set<Long> patientIdSet) {
        //TODO: request data int parallel from patient service and appointment service by patient ids
        return List.of();
    }
}
