package com.example.clinic.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import com.example.clinic.service.EmailService;
import com.example.clinic.util.HeaderUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.clinic.dto.AppointmentDto;
import com.example.clinic.entity.Appointment;
import com.example.clinic.mapper.AppointmentMapper;
import com.example.clinic.service.AppointmentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/appointments")
@AllArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;
    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody AppointmentDto appointmentDto) {
        Appointment appointment = appointmentService.createAppointment(appointmentDto);
        AppointmentDto createdAppointmentDto = appointmentMapper.entityToAppointmentDto(appointment);
        emailService.sendAppointmentEmail(appointmentDto);
        return ResponseEntity.created(URI.create("/api/appointments/" + createdAppointmentDto.id())).body(createdAppointmentDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDto> updateAppointment(@PathVariable Long id, @RequestBody AppointmentDto appointmentDto) {
        Appointment updatedAppointment = appointmentService.updateAppointment(id, appointmentDto);
        return ResponseEntity.ok(appointmentMapper.entityToAppointmentDto(updatedAppointment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok("Appointment with id " + id + " successfully deleted.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointmentMapper.entityToAppointmentDto(appointment));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getDoctors(
            @RequestParam int page,
            @RequestParam int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Appointment> appointmentPage = appointmentService.getAppointments(pageable);

        List<AppointmentDto> appointmentDtos = appointmentPage.getContent().stream()
                .map(appointmentMapper::entityToAppointmentDto)
                .collect(Collectors.toList());

        HttpHeaders headers = HeaderUtils.createPaginationHeaders(appointmentPage);

        return ResponseEntity.ok().headers(headers).body(appointmentDtos);
    }
}
