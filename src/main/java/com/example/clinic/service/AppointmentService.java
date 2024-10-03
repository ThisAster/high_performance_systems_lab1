package com.example.clinic.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.clinic.dto.AppointmentDto;
import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.Patient;
import com.example.clinic.mapper.AppointmentMapper;
import com.example.clinic.repository.AppointmentRepository;
import com.example.clinic.repository.DoctorRepository;
import com.example.clinic.repository.PatientRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentMapper appointmentMapper;

    @Transactional
    public Optional<AppointmentDto> createAppointment(AppointmentDto appointmentDto, Long patientId, Long doctorId) {
        Appointment appointment = appointmentMapper.appointmentDtoToEntity(appointmentDto);

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe with id " + patientId + " not found"));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor with id " + doctorId + " not found"));

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        
        Appointment savedAppointment = appointmentRepository.save(appointment);

        return Optional.of(appointmentMapper.entityToAppointmentDto(savedAppointment));
    }

    public List<Appointment> getAllAppointments() {
        return ((Collection<Appointment>) appointmentRepository.findAll()).stream()
                .collect(Collectors.toList());
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }
    

    @Transactional
    public Optional<Appointment> updateAppointment(Long id, AppointmentDto appointmentDto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment with id " + id + " not found"));

        appointment.setAppointmentDate(appointmentDto.appointmentDate());
        appointment.setDescription(appointmentDto.description());       
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return Optional.of(updatedAppointment);
    };
    

    public List<Appointment> getByDoctorId(Long doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
        
        return appointments.stream()
                .collect(Collectors.toList());
    }

    public List<Appointment> getByPatientId(Long patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        
        return appointments.stream()
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
