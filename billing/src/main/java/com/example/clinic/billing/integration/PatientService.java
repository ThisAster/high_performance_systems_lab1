package com.example.clinic.billing.integration;

import com.example.clinic.billing.dto.PatientDto;

import java.util.List;

public interface PatientService {
    List<PatientDto> getPatientsWithAppointmentsByIds(List<Long> patientIdSet);
}
