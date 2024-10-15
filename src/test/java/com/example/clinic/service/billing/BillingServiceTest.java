package com.example.clinic.service.billing;

import com.example.clinic.app.appointment.dto.AppointmentTypeDTO;
import com.example.clinic.app.appointment.service.AppointmentService;
import com.example.clinic.app.billing.dto.ConsultationDTO;
import com.example.clinic.app.billing.dto.InvoiceDTO;
import com.example.clinic.app.billing.mapper.ConsultationMapper;
import com.example.clinic.app.billing.mapper.InvoiceMapper;
import com.example.clinic.app.billing.service.BillingService;
import com.example.clinic.app.doctor.service.DoctorService;
import com.example.clinic.app.patient.service.PatientService;
import com.example.clinic.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Sql(value = {
//    "/appointments/init.sql" Вот тут можно вставить скрипты, но у нас уже есть в миграции
//}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class BillingServiceTest {

    @Autowired
    private BillingService billingService;


    @Test
    void testGenerateInvoice() {
        List<Long> patientIds = List.of(1L, 2L);

        InvoiceDTO invoiceDTO = billingService.generateInvoice(patientIds);

        for (ConsultationDTO consultation : invoiceDTO.getConsultations()) {
            System.out.println("Patient: " + consultation.getPatient().name());
            System.out.println("Doctor: " + consultation.getDoctor().name());
            System.out.println("Price: " + consultation.getPrice());
        }

        assertNotNull(invoiceDTO);
        assertEquals(3, invoiceDTO.getConsultations().size());

        ConsultationDTO consultation1 = invoiceDTO.getConsultations().get(0);
        assertEquals("John Doe", consultation1.getPatient().name());
        assertEquals("Dr. House", consultation1.getDoctor().name());
        assertEquals(new BigDecimal(250), consultation1.getPrice());

        ConsultationDTO consultation2 = invoiceDTO.getConsultations().get(1);
        assertEquals("Sam Watson", consultation2.getPatient().name());
        assertEquals("Dr. Emily Smith", consultation2.getDoctor().name());
        assertEquals(new BigDecimal(180), consultation2.getPrice());

        assertEquals(new BigDecimal(430), invoiceDTO.getTotalCost());
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
