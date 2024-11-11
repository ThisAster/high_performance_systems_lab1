package com.example.clinic.app.appointment.service;

import com.example.clinic.app.appointment.dto.AppointmentTypeCreationDTO;
import com.example.clinic.app.appointment.entity.AppointmentsType;
import com.example.clinic.app.appointment.mapper.AppoinmentsTypeMapper;
import com.example.clinic.app.doctor.entity.Doctor;
import com.example.clinic.app.doctor.service.DoctorService;
import com.example.clinic.exception.EntityNotFoundException;
import com.example.clinic.app.appointment.repository.AppointmentsTypeRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentsTypeService {

    private final DoctorService doctorService;
    private final AppointmentsTypeRepository appointmentsTypeRepository;
    private final AppoinmentsTypeMapper mapper;
    public AppointmentsType getAppointmentsType(Long appointmentTypeId) {
        return appointmentsTypeRepository.findById(appointmentTypeId)
                .orElseThrow(() -> new EntityNotFoundException("AppointmentType with id " + appointmentTypeId + " not found"));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public AppointmentsType createAppointmentsType(AppointmentTypeCreationDTO dto) {
        Doctor doctor = doctorService.getDoctorById(dto.doctorId());
        AppointmentsType appointmentsType = mapper.appointmentTypeDTOToEntity(dto);
        appointmentsType.setDoctor(doctor);
        return appointmentsTypeRepository.save(appointmentsType);
    }
}
