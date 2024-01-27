package com.example.clinic.patient.controller;

import com.example.clinic.patient.dto.PatientCreationDTO;
import com.example.clinic.patient.entity.Patient;
import com.example.clinic.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PatientCreationController {

    private final PatientService patientService;

    @MessageMapping("/create")
    @SendTo("/api/patients/ws/topic/created")
    public PatientCreationDTO createPatient(PatientCreationDTO patientDto) {
        System.out.println(patientDto);
        Patient patient = patientService.createPatient(patientDto);
        return patientDto;
    }
}
