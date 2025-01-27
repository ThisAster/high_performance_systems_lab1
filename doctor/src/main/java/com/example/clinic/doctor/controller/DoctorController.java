package com.example.clinic.doctor.controller;

import com.example.clinic.doctor.dto.DoctorCreationDTO;
import com.example.clinic.doctor.dto.DoctorDto;
import com.example.clinic.doctor.mapper.DoctorMapper;
import com.example.clinic.doctor.service.DoctorService;
import com.example.clinic.doctor.util.HeaderUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

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
    //tut
    @PutMapping("/{id}")
    public Mono<ResponseEntity<DoctorDto>> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorCreationDTO doctorDto) {
        return doctorService.updateDoctor(id, doctorDto)
                .map(ResponseEntity::ok);
    }
    //tut
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteDoctor(@PathVariable Long id) {
        return doctorService.deleteDoctor(id)
                .then(Mono.just(ResponseEntity.ok("Doctor with id " + id + " successfully deleted.")));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<DoctorDto>> getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    //tut
    @GetMapping
    public Mono<ResponseEntity<List<DoctorDto>>> getDoctors(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 10);
        return doctorService.getDoctors(pageable)
                .map(docPage -> ResponseEntity.ok()
                        .headers(HeaderUtils.createPaginationHeaders(docPage))
                        .body(docPage.getContent()));
    }
}