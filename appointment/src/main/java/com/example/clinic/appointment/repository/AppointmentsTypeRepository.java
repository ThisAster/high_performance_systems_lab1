package com.example.clinic.appointment.repository;

import com.example.clinic.appointment.entity.AppointmentsType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentsTypeRepository extends JpaRepository<AppointmentsType, Long> {
}
