package com.example.clinic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class AppointmentCreationDTO {
    private LocalDateTime appointment_date;
    private Long patient_id;
    private Long appointment_type_id;
}
