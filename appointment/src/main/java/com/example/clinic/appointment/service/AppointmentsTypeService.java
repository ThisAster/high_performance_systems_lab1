package com.example.clinic.appointment.service;

import com.example.clinic.appointment.dto.AppointmentTypeCreationDTO;
import com.example.clinic.appointment.entity.AppointmentsType;
import com.example.clinic.appointment.exception.EntityNotFoundException;
import com.example.clinic.appointment.mapper.AppoinmentsTypeMapper;
import com.example.clinic.appointment.repository.AppointmentsTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppointmentsTypeService {

    private final AppointmentsTypeRepository appointmentsTypeRepository;
    private final AppoinmentsTypeMapper mapper;

    public AppointmentsType getAppointmentsType(Long appointmentTypeId) {
        return appointmentsTypeRepository.findById(appointmentTypeId)
                .orElseThrow(() -> new EntityNotFoundException("AppointmentType with id " + appointmentTypeId + " not found"));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public AppointmentsType createAppointmentsType(AppointmentTypeCreationDTO dto) {
        AppointmentsType appointmentsType = mapper.appointmentTypeDTOToEntity(dto);
        appointmentsType.setDoctor(dto.doctorId());
        return appointmentsTypeRepository.save(appointmentsType);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public AppointmentsType updateAppointmentsType(Long id, AppointmentTypeCreationDTO dto) {
        AppointmentsType existAppontmentsType = this.getAppointmentsType(id);
        existAppontmentsType.setName(dto.name());
        existAppontmentsType.setDescription(dto.description());
        existAppontmentsType.setDoctor(dto.doctorId());
        return appointmentsTypeRepository.save(existAppontmentsType);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteAppointmentsType(Long appointmentTypeId) {
        if (!appointmentsTypeRepository.existsById(appointmentTypeId)) {
            throw new EntityNotFoundException("AppointmentType with id " + appointmentTypeId + " not found");
        }
        appointmentsTypeRepository.deleteById(appointmentTypeId);
    }

    public Page<AppointmentsType> getAllAppointmentsTypes(Pageable pageable) {
        return appointmentsTypeRepository.findAll(pageable);
    }
}
