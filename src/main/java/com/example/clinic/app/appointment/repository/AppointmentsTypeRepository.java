package com.example.clinic.app.appointment.repository;

import com.example.clinic.app.appointment.entity.AppointmentsType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentsTypeRepository extends JpaRepository<AppointmentsType, Long> {
}
