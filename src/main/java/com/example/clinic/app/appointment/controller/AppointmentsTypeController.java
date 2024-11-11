package com.example.clinic.app.appointment.controller;

import com.example.clinic.app.appointment.dto.AppointmentTypeCreationDTO;
import com.example.clinic.app.appointment.entity.AppointmentsType;
import com.example.clinic.app.appointment.service.AppointmentsTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/appointments-type")
@RequiredArgsConstructor
public class AppointmentsTypeController {
    private final AppointmentsTypeService appointmentsTypeService;

    @PostMapping
    public ResponseEntity<AppointmentTypeCreationDTO> createAppointmentType(@RequestBody AppointmentTypeCreationDTO appointmentTypeDTO) {
        AppointmentsType appointmentsType = appointmentsTypeService.createAppointmentsType(appointmentTypeDTO);
        return ResponseEntity.created(URI.create("/api/appointments/" + appointmentsType.getId()))
                .body(appointmentTypeDTO);
    }
}
