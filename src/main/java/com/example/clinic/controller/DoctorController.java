package com.example.clinic.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}