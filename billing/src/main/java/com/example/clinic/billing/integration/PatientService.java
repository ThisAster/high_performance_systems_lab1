package com.example.clinic.billing.integration;

import com.example.clinic.billing.dto.PatientDto;

import java.util.List;
import java.util.Set;

public interface PatientService {
    List<PatientDto> getPatientsWithAppointmentsByIds(Set<Long> patientIdSet);
}
