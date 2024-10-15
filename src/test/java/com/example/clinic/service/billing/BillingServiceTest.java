package com.example.clinic.service.billing;

import com.example.clinic.app.appointment.dto.AppointmentCreationDTO;
import com.example.clinic.app.appointment.dto.AppointmentTypeDTO;
import com.example.clinic.app.appointment.entity.Appointment;
import com.example.clinic.app.appointment.service.AppointmentService;
import com.example.clinic.app.billing.dto.ConsultationDTO;
import com.example.clinic.app.billing.dto.InvoiceDTO;
import com.example.clinic.app.billing.mapper.ConsultationMapper;
import com.example.clinic.app.billing.mapper.InvoiceMapper;
import com.example.clinic.app.billing.service.BillingService;
import com.example.clinic.app.doctor.dto.DoctorDto;
import com.example.clinic.app.doctor.entity.Doctor;
import com.example.clinic.app.doctor.mapper.DoctorMapper;
import com.example.clinic.app.doctor.service.DoctorService;
import com.example.clinic.app.patient.dto.PatientDto;
import com.example.clinic.app.patient.entity.Patient;
import com.example.clinic.app.patient.repository.PatientRepository;
import com.example.clinic.app.doctor.repository.DoctorRepository;
import com.example.clinic.app.patient.service.PatientService;
import com.example.clinic.exception.EntityNotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BillingServiceTest {

    @Autowired
    private BillingService billingService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ConsultationMapper consultationMapper;
    private InvoiceMapper invoiceMapper;

    @Test
    void testGenerateInvoice() {
        PatientDto patientDto1 = new PatientDto(
                1L,
                "John Doe",
                LocalDate.of(1965, 4, 20),
                "johnlover@example.com");
        PatientDto patientDto2 = new PatientDto(
                2L,
                "Sam Watson",
                LocalDate.of(1980, 5, 20),
                "samdoctor@example.com");

        patientService.createPatient(patientDto1);
        patientService.createPatient(patientDto2);

        DoctorDto doctorDto1 = new DoctorDto(
                1L,
                "Dr. House",
                "Cardiologist"
        );

        DoctorDto doctorDto2 = new DoctorDto(
                2L,
                "Dr. Emily Smith",
                "Dermatologist"
        );

        doctorService.createDoctor(doctorDto1);
        doctorService.createDoctor(doctorDto2);

        AppointmentTypeDTO appointmentTypeDTO1 = new AppointmentTypeDTO(
                1L,
                "Cardiology Consultation",
                "Comprehensive heart health consultation",
                60,
                new BigDecimal("250.00"),
                doctorDto1
        );

        AppointmentTypeDTO appointmentTypeDTO2 = new AppointmentTypeDTO(
                2L,
                "Skin Checkup",
                "Routine skin examination to check for any abnormalities",
                45,
                new BigDecimal("180.00"),
                doctorDto2
        );

        AppointmentCreationDTO appointmentCreationDTO1 = new AppointmentCreationDTO(
                LocalDateTime.of(2024, 12, 5, 10, 45),
                1L,
                appointmentTypeDTO1.id()
        );

        AppointmentCreationDTO appointmentCreationDTO2 = new AppointmentCreationDTO(
                LocalDateTime.of(2024, 11, 5, 10, 45),
                2L,
                appointmentTypeDTO2.id()
        );

        appointmentService.createAppointment(appointmentCreationDTO1);
        appointmentService.createAppointment(appointmentCreationDTO2);

        List<Long> patientIds = Arrays.asList(patientDto1.id(), patientDto2.id());

        InvoiceDTO invoiceDTO = billingService.generateInvoice(patientIds);

        for (ConsultationDTO consultation : invoiceDTO.getConsultations()) {
            System.out.println("Patient: " + consultation.getPatient().name());
            System.out.println("Doctor: " + consultation.getDoctor().name());
            System.out.println("Price: " + consultation.getPrice());
        }

        assertNotNull(invoiceDTO);
        assertEquals(2, invoiceDTO.getConsultations().size());

        ConsultationDTO consultation1 = invoiceDTO.getConsultations().get(0);
        assertEquals("John Doe", consultation1.getPatient().name());
        assertEquals("Dr. House", consultation1.getDoctor().name());
        assertEquals(250.00, consultation1.getPrice());

        ConsultationDTO consultation2 = invoiceDTO.getConsultations().get(1);
        assertEquals("Sam Watson", consultation2.getPatient().name());
        assertEquals("Dr. Emily Smith", consultation2.getDoctor().name());
        assertEquals(180.00, consultation2.getPrice());

        assertEquals(430.00, invoiceDTO.getTotalCost());
    }




    @Test
    void testGenerateInvoicePatientNotFound() {
        List<Long> invalidPatientIds = Arrays.asList(999L);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            billingService.generateInvoice(invalidPatientIds);
        });

        assertEquals("Patients with ids 999 not found.", exception.getMessage());
    }

    private void printAppointmentTypeDetails(AppointmentTypeDTO appointmentTypeDTO) {
        System.out.println("Appointment Type Details:");
        System.out.println("ID: " + appointmentTypeDTO.id());
        System.out.println("Name: " + appointmentTypeDTO.name());
        System.out.println("Description: " + appointmentTypeDTO.description());
        System.out.println("Duration: " + appointmentTypeDTO.duration() + " minutes");
        System.out.println("Price: $" + appointmentTypeDTO.price());
        System.out.println("Doctor: " + (appointmentTypeDTO.doctor() != null ? appointmentTypeDTO.doctor().name() : "No doctor assigned"));
        System.out.println("------------------------------");
    }
}
