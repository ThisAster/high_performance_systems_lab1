package com.example.clinic.app.doctor.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import com.example.clinic.app.doctor.dto.DoctorCreationDTO;
import com.example.clinic.model.PageArgument;
import com.example.clinic.util.HeaderUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.clinic.app.doctor.dto.DoctorDto;
import com.example.clinic.app.doctor.entity.Doctor;
import com.example.clinic.app.doctor.mapper.DoctorMapper;
import com.example.clinic.app.doctor.service.DoctorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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