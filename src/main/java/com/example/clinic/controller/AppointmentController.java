package com.example.clinic.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import com.example.clinic.dto.AppointmentCreationDTO;
import com.example.clinic.service.EmailService;
import com.example.clinic.util.HeaderUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Slf4j
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;
    // private final EmailService emailService;

    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody AppointmentCreationDTO appointmentDto) {
        Appointment appointment = appointmentService.createAppointment(appointmentDto);
        AppointmentDto createdAppointmentDto = appointmentMapper.entityToAppointmentDto(appointment);

        new Thread(() -> {
            try {
               // emailService.sendCreationEmail(appointment);
            } catch (Exception e) {
                log.error("Failed to send email", e);
            }
        }).start();

        return ResponseEntity.created(URI.create("/api/appointments/" + createdAppointmentDto.getId())).body(createdAppointmentDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDto> updateAppointment(@PathVariable Long id, @RequestBody AppointmentCreationDTO appointmentDto) {
        Appointment updatedAppointment = appointmentService.updateAppointment(id, appointmentDto);

        new Thread(() -> {
            try {
              //  emailService.sendUpdateEmail(updatedAppointment);
            } catch (Exception e) {
                log.error("Failed to send email", e);
            }
        }).start();

        return ResponseEntity.ok(appointmentMapper.entityToAppointmentDto(updatedAppointment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);

        new Thread(() -> {
            try {
              //  emailService.sendDeletionEmail(id);
            } catch (Exception e) {
                log.error("Failed to send email", e);
            }
        }).start();
        return ResponseEntity.ok("Appointment with id " + id + " successfully deleted.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointmentMapper.entityToAppointmentDto(appointment));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAppointments(
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
