package com.example.clinic.billing.integration;

import com.example.clinic.billing.dto.PatientDto;

import java.util.Set;

public interface PatientService {
    Set<PatientDto> getPatientsWithAppointmentsByIds(Set<Long> patientIdSet);
}
