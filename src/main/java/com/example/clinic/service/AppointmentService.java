package com.example.clinic.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.clinic.dto.AppointmentDto;
import com.example.clinic.dto.DoctorDto;
import com.example.clinic.dto.PatientDto;
import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.Patient;
import com.example.clinic.mapper.AppointmentMapper;
import com.example.clinic.mapper.DoctorMapper;
import com.example.clinic.mapper.PatientMapper;
import com.example.clinic.repository.AppointmentRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;

    private final AppointmentMapper appointmentMapper;
    private final PatientMapper patientMapper;
    private final DoctorMapper doctorMapper;

    @Transactional
    public Optional<AppointmentDto> createAppointment(AppointmentDto appointmentDto, Long patientId, Long doctorId) {
        Appointment appointment = appointmentMapper.appointmentDtoToEntity(appointmentDto);

        PatientDto patientDto = patientService.getPatientById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe with id " + patientId + " not found"));

        DoctorDto doctorDto = doctorService.getDoctorById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor with id " + doctorId + " not found"));

        Patient patient = patientMapper.patientDtoToEntity(patientDto);
        Doctor doctor = doctorMapper.doctorDtoToEntity(doctorDto);

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        
        Appointment savedAppointment = appointmentRepository.save(appointment);

        return Optional.of(appointmentMapper.entityToAppointmentDto(savedAppointment));
    }

    public List<AppointmentDto> getAllAppointments() {
        return ((Collection<Appointment>) appointmentRepository.findAll()).stream()
                .map(appointmentMapper::entityToAppointmentDto)
                .collect(Collectors.toList());
    }

    public Optional<AppointmentDto> getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .map(appointmentMapper::entityToAppointmentDto);
    }
    

    @Transactional
    public Optional<AppointmentDto> updateAppointment(Long id, AppointmentDto appointmentDto) {
        return appointmentRepository.findById(id).map(appointment -> {
            appointmentMapper.updateEntityFromDto(appointment, appointmentDto);
            Appointment updatedAppointment = appointmentRepository.save(appointment);
            return appointmentMapper.entityToAppointmentDto(updatedAppointment);
        });
    }

    public List<AppointmentDto> getByDoctorId(Long doctorId) {
        DoctorDto doctorDto = doctorService.getDoctorById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor with id " + doctorId + " not found"));
    
        Doctor doctor = doctorMapper.doctorDtoToEntity(doctorDto);
        
        List<Appointment> appointments = appointmentRepository.findByDoctor(doctor);
        
        return appointments.stream()
                .map(appointmentMapper::entityToAppointmentDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getByPatientId(Long patientId) {
        PatientDto patientDto = patientService.getPatientById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient with id " + patientId + " not found"));
    
        Patient patient = patientMapper.patientDtoToEntity(patientDto);
        
        List<Appointment> appointments = appointmentRepository.findByPatient(patient);
        
        return appointments.stream()
                .map(appointmentMapper::entityToAppointmentDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
