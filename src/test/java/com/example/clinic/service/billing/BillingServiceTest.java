package com.example.clinic.service.billing;

import com.example.clinic.app.billing.dto.ConsultationDTO;
import com.example.clinic.app.billing.dto.InvoiceDTO;
import com.example.clinic.app.billing.service.BillingService;
import com.example.clinic.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
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

        assertNotNull(invoiceDTO);
        assertEquals(3, invoiceDTO.getConsultations().size());

        ConsultationDTO consultation1 = invoiceDTO.getConsultations().getFirst();
        assertEquals("John Doe", consultation1.getPatient().name());
        assertEquals("Dr. Alice Brown", consultation1.getDoctor().name());
        assertEquals(new BigDecimal("1200.00"), consultation1.getPrice());

        ConsultationDTO consultation2 = invoiceDTO.getConsultations().get(1);
        assertEquals("John Doe", consultation2.getPatient().name());
        assertEquals("Dr. Carol Davis", consultation2.getDoctor().name());
        assertEquals(new BigDecimal("2000.00"), consultation2.getPrice());

        ConsultationDTO consultation3 = invoiceDTO.getConsultations().get(2);
        assertEquals("Jane Smith", consultation3.getPatient().name());
        assertEquals("Dr. Bob Lee", consultation3.getDoctor().name());
        assertEquals(new BigDecimal("500.00"), consultation3.getPrice());

        assertEquals(new BigDecimal("3700.00"), invoiceDTO.getTotalCost());
    }

    @Test
    void testGenerateInvoicePatientNotFound() {
        List<Long> invalidPatientIds = List.of(999L);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                billingService.generateInvoice(invalidPatientIds));

        assertEquals("No patients found for the provided IDs", exception.getMessage());
    }
}
