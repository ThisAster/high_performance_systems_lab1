package com.example.clinic.app.appointment.service;

import com.example.clinic.app.appointment.entity.AppointmentsType;
import com.example.clinic.exception.EntityNotFoundException;
import com.example.clinic.app.appointment.repository.AppointmentsTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentsTypeService {

    private final AppointmentsTypeRepository appointmentsTypeRepository;
    public AppointmentsType getAppointmentsType(Long appointmentTypeId) {
        return appointmentsTypeRepository.findById(appointmentTypeId)
                .orElseThrow(() -> new EntityNotFoundException("AppointmentType with id " + appointmentTypeId + " not found"));
    }
}
