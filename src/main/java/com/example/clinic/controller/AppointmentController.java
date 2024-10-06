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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody AppointmentDto appointmentDto,
                                                             @RequestParam Long patientId,
                                                             @RequestParam Long doctorId) {
        Appointment appointment = appointmentService.createAppointment(appointmentDto, patientId, doctorId);
        AppointmentDto createdAppointmentDto = appointmentMapper.entityToAppointmentDto(appointment);
        return ResponseEntity.created(URI.create("/api/appointments/" + createdAppointmentDto.id())).body(createdAppointmentDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDto> updateAppointment(@PathVariable Long id, @RequestBody AppointmentDto appointmentDto) {
        Appointment updatedAppointment = appointmentService.updateAppointment(id, appointmentDto);
        return ResponseEntity.ok(appointmentMapper.entityToAppointmentDto(updatedAppointment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointmentMapper.entityToAppointmentDto(appointment));
    }
}
