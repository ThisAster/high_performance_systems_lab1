package com.example.clinic.app.patient.controller;

import java.net.URI;

import com.example.clinic.app.patient.dto.PatientCreationDTO;
import com.example.clinic.model.PageArgument;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.clinic.app.patient.dto.PatientDto;
import com.example.clinic.app.patient.entity.Patient;
import com.example.clinic.app.patient.mapper.PatientMapper;
import com.example.clinic.app.patient.service.PatientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@Slf4j
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    @PostMapping
    public ResponseEntity<PatientCreationDTO> createPatient(@Valid @RequestBody PatientCreationDTO patientDto) {
        Patient patient = patientService.createPatient(patientDto);

        return ResponseEntity.created(URI.create("/api/patients/" + patient.getId()))
                .body(patientDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientCreationDTO> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientCreationDTO patientDto) {
        patientService.updatePatient(id, patientDto);
        return ResponseEntity.ok(patientDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok("Patient with id " + id + " successfully deleted.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        PatientDto patientDto = patientMapper.entityToPatientDto(patient);
        return ResponseEntity.ok(patientDto);
    }

    @GetMapping
    public ResponseEntity<Page<PatientDto>> getPatients(
            PageArgument page
    ) {
        Page<Patient> patientPage = patientService.getPatients(page.getPageRequest());
        Page<PatientDto> response = patientPage.map(patientMapper::entityToPatientDto);
        return ResponseEntity.ok(response);
    }
}
