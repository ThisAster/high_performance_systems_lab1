package com.example.clinic.app.appointment.controller;

import com.example.clinic.app.appointment.dto.AppointmentTypeCreationDTO;
import com.example.clinic.app.appointment.dto.AppointmentTypeDTO;
import com.example.clinic.app.appointment.entity.AppointmentsType;
import com.example.clinic.app.appointment.mapper.AppoinmentsTypeMapper;
import com.example.clinic.app.appointment.service.AppointmentsTypeService;
import com.example.clinic.model.PageArgument;
import com.example.clinic.util.HeaderUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments-type")
@RequiredArgsConstructor
public class AppointmentsTypeController {
    private final AppointmentsTypeService appointmentsTypeService;
    private final AppoinmentsTypeMapper appointmentsTypeMapper;

    @PostMapping
    public ResponseEntity<AppointmentTypeCreationDTO> createAppointmentType(@Valid @RequestBody AppointmentTypeCreationDTO appointmentTypeDTO) {
        AppointmentsType appointmentsType = appointmentsTypeService.createAppointmentsType(appointmentTypeDTO);
        return ResponseEntity.created(URI.create("/api/appointments/" + appointmentsType.getId()))
                .body(appointmentTypeDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentTypeDTO> getAppointmentType(@PathVariable Long id) {
        AppointmentsType appointmentsType = appointmentsTypeService.getAppointmentsType(id);
        return ResponseEntity.ok(appointmentsTypeMapper.entityToAppointmentTypeDTO(appointmentsType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentTypeCreationDTO> updateAppointmentType(@PathVariable Long id, @Valid @RequestBody AppointmentTypeCreationDTO appointmentTypeDTO) {
        appointmentsTypeService.updateAppointmentsType(id, appointmentTypeDTO);
        return ResponseEntity.ok(appointmentTypeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointmentType(@PathVariable Long id) {
        appointmentsTypeService.deleteAppointmentsType(id);
        return ResponseEntity.ok("AppointmentsType with id " + id + " successfully deleted.");
    }

    @GetMapping
    public ResponseEntity<List<AppointmentTypeDTO>> getAllAppointmentTypes(
            PageArgument page
    ) {
        Page<AppointmentsType> appointmentsTypePage = appointmentsTypeService.getAllAppointmentsTypes(page.getPageRequest());
        List<AppointmentTypeDTO> response = appointmentsTypePage.getContent().stream()
                .map(appointmentsTypeMapper::entityToAppointmentTypeDTO)
                .collect(Collectors.toList());

        HttpHeaders headers = HeaderUtils.createPaginationHeaders(appointmentsTypePage);
        return ResponseEntity.ok().headers(headers).body(response);
    }
}
