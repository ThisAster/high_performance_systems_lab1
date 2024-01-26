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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<ResponseEntity<DoctorDto>> createDoctor(@Valid @RequestBody DoctorCreationDTO doctorDto) {
        return doctorService.createDoctor(doctorDto)
                .map(doc -> ResponseEntity.created(URI.create("/api/doctors/" + doc.id())).body(doc));
    }

    @PutMapping("/{id}")
    public Mono<DoctorDto> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorCreationDTO doctorDto) {
        return doctorService.updateDoctor(id, doctorDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("Doctor with id " + id + " successfully deleted.");
    }

    @GetMapping("/{id}")
    public Mono<DoctorDto> getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    @GetMapping
    public Mono<ResponseEntity<List<DoctorDto>>> getDoctors(
            PageArgument page
    ) {
        return doctorService.getDoctors(page.getPageRequest())
                .map(docPage -> ResponseEntity.ok()
                        .headers(HeaderUtils.createPaginationHeaders(docPage))
                        .body(docPage.getContent()));
    }
}