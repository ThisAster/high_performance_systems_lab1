package com.example.clinic.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import com.example.clinic.util.HeaderUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.clinic.dto.DoctorDto;
import com.example.clinic.entity.Doctor;
import com.example.clinic.mapper.DoctorMapper;
import com.example.clinic.service.DoctorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

  private final DoctorService doctorService;
  private final DoctorMapper doctorMapper;

  @PostMapping
  public ResponseEntity<DoctorDto> createDoctor(@RequestBody @Valid DoctorDto doctorDto) {
      Doctor doctor = doctorService.createDoctor(doctorDto);
      DoctorDto createdDoctorDto = doctorMapper.entityToDoctorDto(doctor);
      return ResponseEntity.created(URI.create("/api/doctors/" + createdDoctorDto.id())).body(createdDoctorDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<DoctorDto> updateDoctor(@PathVariable Long id, @RequestBody @Valid DoctorDto doctorDto) {
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
    public ResponseEntity<List<DoctorDto>> getAllDoctors(
            @RequestParam int page,
            @RequestParam int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Doctor> doctorPage = doctorService.getDoctors(pageable);

        List<DoctorDto> doctorDtos = doctorPage.getContent().stream()
                .map(doctorMapper::entityToDoctorDto)
                .collect(Collectors.toList());

        HttpHeaders headers = HeaderUtils.createPaginationHeaders(doctorPage);

        return ResponseEntity.ok().headers(headers).body(doctorDtos);
    }
}