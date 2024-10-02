/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.clinic.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.clinic.dto.AppointmentDto;
import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.Patient;
import com.example.clinic.repository.AppointmentRepository;

import jakarta.transaction.Transactional;

/**
 *
 * @author thisaster
 */
@Service
public class AppointmentService {

    private AppointmentRepository appointmentRepository;

    private ModelMapper appointmentMapper;

    @Autowired
    public void setAppointmentRepository(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Autowired
    public void setAppoinmentMapper(ModelMapper appoinmentMapper) {
        this.appointmentMapper = appoinmentMapper;
    }

    @Transactional
    public AppointmentDto createAppointment(AppointmentDto appointmentDto, Patient patientDto, Doctor doctorDto) {
        Appointment appointment = appointmentMapper.map(appointmentDto, Appointment.class);
        Patient patient = appointmentMapper.map(patientDto, Patient.class);
        Doctor doctor = appointmentMapper.map(doctorDto, Doctor.class);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.map(savedAppointment, AppointmentDto.class);
    }

    public List<AppointmentDto> getAllAppointments() {
        return ((List<Appointment>) appointmentRepository.findAll()).stream()
                .map(appointment -> appointmentMapper.map(appointment, AppointmentDto.class))
                .collect(Collectors.toList());
    }

    public AppointmentDto getAppointmentById(Long id) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(id);
        if (appointmentOpt.isEmpty()) {
            throw new IllegalArgumentException("Appointment with id " + id + " not found");
        }
        return appointmentMapper.map(appointmentOpt.get(), AppointmentDto.class);
    }

    @Transactional
    public AppointmentDto updateAppointment(Long id, AppointmentDto appointmentDto) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(id);
        if (appointmentOpt.isEmpty()) {
            throw new IllegalArgumentException("Appointment with id " + id + " not found");
        }
        Appointment appointment = appointmentOpt.get();
        appointment.setAppointmentDate(appointmentDto.appointmentDate());
        appointment.setDescription(appointmentDto.description());
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.map(updatedAppointment, AppointmentDto.class);
    }

    @Transactional
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new IllegalArgumentException("Appointment with id " + id + " not found");
        }
        appointmentRepository.deleteById(id);
    }
}
