package com.example.clinic.patient.controller;

import com.example.clinic.patient.dto.PatientCreationDTO;
import com.example.clinic.patient.dto.PatientDto;
import com.example.clinic.patient.entity.Patient;
import com.example.clinic.patient.mapper.PatientMapper;
import com.example.clinic.patient.model.PageArgument;
import com.example.clinic.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

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

    @GetMapping("/some")
    public ResponseEntity<List<PatientDto>> getPatientsByIds(@RequestParam List<Long> patients) {
        return ResponseEntity.ok(patientService.getPatientsByIds(patients));
    }
}
