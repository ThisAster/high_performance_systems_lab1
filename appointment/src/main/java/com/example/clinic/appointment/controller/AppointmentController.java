package com.example.clinic.appointment.controller;

import com.example.clinic.appointment.dto.AppointmentCreationDTO;
import com.example.clinic.appointment.dto.AppointmentDto;
import com.example.clinic.appointment.dto.AppointmentFilterRequest;
import com.example.clinic.appointment.entity.Appointment;
import com.example.clinic.appointment.mapper.AppointmentMapper;
import com.example.clinic.appointment.model.PageArgument;
import com.example.clinic.appointment.service.AppointmentService;
import com.example.clinic.appointment.util.HeaderUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/appointments")
@AllArgsConstructor
@Slf4j
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;

    @PostMapping
    public ResponseEntity<AppointmentCreationDTO> createAppointment(@Valid @RequestBody AppointmentCreationDTO appointmentDto) {

        Appointment appointment = appointmentService.createAppointment(appointmentDto);

        return ResponseEntity.created(URI.create("/api/appointments/" + appointment.getId()))
                .body(appointmentDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentCreationDTO> updateAppointment(@PathVariable Long id, @Valid @RequestBody AppointmentCreationDTO appointmentDto) {
        appointmentService.updateAppointment(id, appointmentDto);

        return ResponseEntity.ok(appointmentDto);
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
    public ResponseEntity<List<AppointmentDto>> getAppointments(PageArgument page) {
        Page<Appointment> appointmentPage = appointmentService.getAppointments(page.getPageRequest());

        List<AppointmentDto> appointmentDtos = appointmentPage.getContent().stream()
                .map(appointmentMapper::entityToAppointmentDto)
                .collect(Collectors.toList());

        HttpHeaders headers = HeaderUtils.createPaginationHeaders(appointmentPage);

        return ResponseEntity.ok().headers(headers).body(appointmentDtos);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<AppointmentDto>> filterAppointments(AppointmentFilterRequest filter) {
        return ResponseEntity.ok(appointmentService.filter(filter));
    }

}
