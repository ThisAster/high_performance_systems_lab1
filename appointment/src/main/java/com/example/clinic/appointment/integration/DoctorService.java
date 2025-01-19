package com.example.clinic.appointment.integration;

import com.example.clinic.appointment.dto.DoctorDto;

public interface DoctorService {
    DoctorDto getDoctorById(Long doctor);
}
