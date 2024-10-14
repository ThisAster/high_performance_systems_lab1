package com.example.clinic.controller;

import java.net.URI;

import com.example.clinic.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.clinic.dto.PatientDto;
import com.example.clinic.entity.Patient;
import com.example.clinic.mapper.PatientMapper;
import com.example.clinic.service.PatientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@Slf4j
public class PatientController {

    private final PatientService patientService;
    private final EmailService emailService;
    private final PatientMapper patientMapper;

    @PostMapping
    public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto patientDto) {
        new Thread(() -> {
            try {
                emailService.sendPatientEmail(patientDto, "Registration on ITMO clinic platform");
            } catch (Exception e) {
                log.error("Failed to send email", e);
            }
        }).start();
        Patient patient = patientService.createPatient(patientDto);
        PatientDto createdPatientDto = patientMapper.entityToPatientDto(patient);
        return ResponseEntity.created(URI.create("/api/patients/" + createdPatientDto.id())).body(createdPatientDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable Long id, @RequestBody PatientDto patientDto) {
        Patient updatedPatient = patientService.updatePatient(id, patientDto);
        PatientDto updatedPatientDto = patientMapper.entityToPatientDto(updatedPatient);
        return ResponseEntity.ok(updatedPatientDto);
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
    public ResponseEntity<Page<PatientDto>> getRecipes(
            @RequestParam int page,
            @RequestParam int size
    ) {
        Page<Patient> patientPage = patientService.getPatients(PageRequest.of(page, size));
        Page<PatientDto> response = patientPage.map(patientMapper::entityToPatientDto);
        return ResponseEntity.ok(response);
    }
}
