package com.example.clinic.billing.integration.feign;

import com.example.clinic.billing.dto.AppointmentDto;
import com.example.clinic.billing.dto.PatientDto;
import com.example.clinic.billing.integration.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientClient implements PatientService {
    private final PatientFeignClient patientClient;
    private final AppointmentFeignClient appointmentClient;

    @Override
    public List<PatientDto> getPatientsWithAppointmentsByIds(List<Long> patientIds) {
        var patients = patientClient.findByIds(patientIds);
        var appointments = appointmentClient.findByPatients(patientIds)
                .stream()
                .collect(Collectors.groupingBy(AppointmentDto::getPatientId));
        return patients.stream().map(p -> new PatientDto(
                p.id(), p.name(), p.dateOfBirth(), p.email(), appointments.getOrDefault(p.id(), Collections.emptyList())
        )).toList();
    }
}
