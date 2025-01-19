package com.example.clinic.doctor.controller;

import com.example.clinic.doctor.dto.DoctorCreationDTO;
import com.example.clinic.doctor.dto.DoctorDto;
import com.example.clinic.doctor.entity.Doctor;
import com.example.clinic.doctor.mapper.DoctorMapper;
import com.example.clinic.doctor.model.PageArgument;
import com.example.clinic.doctor.service.DoctorService;
import com.example.clinic.doctor.util.HeaderUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;

    @PostMapping
    public ResponseEntity<DoctorCreationDTO> createDoctor(@Valid @RequestBody DoctorCreationDTO doctorDto) {
        Doctor doctor = doctorService.createDoctor(doctorDto);
        return ResponseEntity.created(URI.create("/api/doctors/" + doctor.getId())).body(doctorDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorCreationDTO doctorDto) {
        Doctor doctor = doctorService.updateDoctor(id, doctorDto);
        DoctorDto updatedDoctorDto = doctorMapper.entityToDoctorDto(doctor);
        return ResponseEntity.ok(updatedDoctorDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("Doctor with id " + id + " successfully deleted.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorService.getDoctorById(id);
        DoctorDto doctorDto = doctorMapper.entityToDoctorDto(doctor);
        return ResponseEntity.ok(doctorDto);
    }

    @GetMapping
    public ResponseEntity<List<DoctorDto>> getDoctors(
            PageArgument page
    ) {
        Page<Doctor> doctorPage = doctorService.getDoctors(page.getPageRequest());

        List<DoctorDto> doctorDtos = doctorPage.getContent().stream()
                .map(doctorMapper::entityToDoctorDto)
                .collect(Collectors.toList());

        HttpHeaders headers = HeaderUtils.createPaginationHeaders(doctorPage);

        return ResponseEntity.ok().headers(headers).body(doctorDtos);
    }
}