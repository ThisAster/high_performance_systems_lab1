package com.example.clinic.appointment.controller;

import com.example.clinic.appointment.dto.AppointmentCreationDTO;
import com.example.clinic.appointment.dto.AppointmentDto;
import com.example.clinic.appointment.entity.Appointment;
import com.example.clinic.appointment.integration.EmailService;
import com.example.clinic.appointment.mapper.AppointmentMapper;
import com.example.clinic.appointment.service.AppointmentService;
import com.example.clinic.appointment.model.PageArgument;
import com.example.clinic.appointment.util.HeaderUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments")
@AllArgsConstructor
@Slf4j
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;
    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<AppointmentCreationDTO> createAppointment(@Valid @RequestBody AppointmentCreationDTO appointmentDto) {

        Appointment appointment = appointmentService.createAppointment(appointmentDto);

        new Thread(() -> {
            try {
                emailService.sendAppointmentEmail(appointment, "You have signed up for an appointment");
            } catch (Exception e) {
                log.error("Failed to send email", e);
            }
        }).start();

        return ResponseEntity.created(URI.create("/api/appointments/" + appointment.getId()))
                .body(appointmentDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentCreationDTO> updateAppointment(@PathVariable Long id, @Valid @RequestBody AppointmentCreationDTO appointmentDto) {
        Appointment updatedAppointment = appointmentService.updateAppointment(id, appointmentDto);

        new Thread(() -> {
            try {
                emailService.sendAppointmentEmail(updatedAppointment, "Information about your appointment has been updated.");
            } catch (Exception e) {
                log.error("Failed to send email", e);
            }
        }).start();

        return ResponseEntity.ok(appointmentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);

        new Thread(() -> {
            try {
                emailService.sendAppointmentEmail(appointment, "Your appointment has been canceled.");
            } catch (Exception e) {
                log.error("Failed to send email", e);
            }
        }).start();

        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok("Appointment with id " + id + " successfully deleted.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointmentMapper.entityToAppointmentDto(appointment));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAppointments(
            PageArgument page
    ) {
        Page<Appointment> appointmentPage = appointmentService.getAppointments(page.getPageRequest());

        List<AppointmentDto> appointmentDtos = appointmentPage.getContent().stream()
                .map(appointmentMapper::entityToAppointmentDto)
                .collect(Collectors.toList());

        HttpHeaders headers = HeaderUtils.createPaginationHeaders(appointmentPage);

        return ResponseEntity.ok().headers(headers).body(appointmentDtos);
    }
}
