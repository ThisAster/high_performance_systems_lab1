package com.example.clinic.appointment.service;

import com.example.clinic.appointment.dto.AppointmentFilterRequest;
import com.example.clinic.appointment.entity.Appointment;
import com.example.clinic.appointment.entity.AppointmentsType;
import com.example.clinic.appointment.integration.DoctorService;
import com.example.clinic.appointment.integration.EmailService;
import com.example.clinic.appointment.integration.PatientService;
import com.example.clinic.appointment.mapper.AppointmentMapper;
import com.example.clinic.appointment.repository.AppointmentRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {

    private final AppointmentService service;
    private final AppointmentRepository mockAppointmentRepository = mock(AppointmentRepository.class);
    private final AppointmentMapper mockAppointmentMapper = mock(AppointmentMapper.class);
    private final AppointmentsTypeService mockAppointmentsTypeService = mock(AppointmentsTypeService.class);
    private final EmailService mockEmailService = mock(EmailService.class);
    private final PatientService mockPatientService = mock(PatientService.class);
    private final DoctorService mockDoctorService = mock(DoctorService.class);

    AppointmentServiceTest() {


        service = new AppointmentService(
                mockAppointmentRepository,
                mockAppointmentMapper,
                mockAppointmentsTypeService,
                mockEmailService,
                mockPatientService,
                mockDoctorService
        );
    }

    @Test
    void filterByPatients() {
        var fiter = new AppointmentFilterRequest(
                List.of(1L, 2L, 3L),
                null,
                null,
                null);

        doReturn(List.of(
                Appointment.builder()
                        .id(1L)
                        .patient(1L)
                        .appointmentDate(LocalDateTime.now().minusHours(2))
                        .appointmentType(AppointmentsType.builder()
                                .id(1L)
                                .description("Type 1")
                                .doctor(1L)
                                .duration(15)
                                .build())
                        .build(),
                Appointment.builder()
                        .id(2L)
                        .patient(2L)
                        .appointmentDate(LocalDateTime.now().minusHours(1))
                        .appointmentType(AppointmentsType.builder()
                                .id(2L)
                                .description("Type 2")
                                .doctor(2L)
                                .duration(15)
                                .build())
                        .build(),
                Appointment.builder()
                        .id(3L)
                        .patient(3L)
                        .appointmentDate(LocalDateTime.now().minusMinutes(30))
                        .appointmentType(AppointmentsType.builder()
                                .id(3L)
                                .description("Type 2")
                                .doctor(3L)
                                .duration(15)
                                .build())
                        .build()
        )).when(mockAppointmentRepository).findByPatientIn(anyList());

        var result = service.filter(fiter);

        assertEquals(3, result.size());
    }
}