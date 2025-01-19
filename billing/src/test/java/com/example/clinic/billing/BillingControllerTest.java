package com.example.clinic.billing;

import com.example.clinic.billing.dto.AppointmentDto;
import com.example.clinic.billing.dto.AppointmentTypeDTO;
import com.example.clinic.billing.dto.DoctorDto;
import com.example.clinic.billing.dto.PatientDto;
import com.example.clinic.billing.integration.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
class BillingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PatientService patientService;

    @Test
    void getAppointments() throws Exception {
        final var test = List.of(
                new PatientDto(
                        2L, "Jane Smith", null, "jane@example.com",
                        List.of(
                                new AppointmentDto(
                                        2L,
                                        LocalDateTime.parse("2023-06-15T14:30"),
                                        2L,
                                        new AppointmentTypeDTO(2L,
                                                "Вырезание мозоли",
                                                "Операция по вырезанию среднего размера мозоли",
                                                10,
                                                BigDecimal.valueOf(500),
                                                new DoctorDto(
                                                        2L,
                                                        "Dr. Bob Lee",
                                                        "Neurology"
                                                )
                                        )
                                ),
                                new AppointmentDto(
                                        4L,
                                        LocalDateTime.parse("2025-11-15T01:38:03"),
                                        2L,
                                        new AppointmentTypeDTO(2L,
                                                "Вырезание мозоли",
                                                "Операция по вырезанию среднего размера мозоли",
                                                10,
                                                BigDecimal.valueOf(500),
                                                new DoctorDto(
                                                        2L,
                                                        "Dr. Bob Lee",
                                                        "Neurology"
                                                )
                                        )
                                )
                        )
                )
        );

        when(patientService.getPatientsWithAppointmentsByIds(List.of(2L))).thenReturn(test);

        List<Integer> patientIds = List.of(2);

        mockMvc.perform(get("/api/billing/invoice")
                        .param("patients", patientIds.stream()
                                .map(String::valueOf)
                                .toArray(String[]::new)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total_cost").value("1000"))
                .andExpect(jsonPath("$.consultations.length()").value(2));
    }


    @Test
    void testGenerateInvoicePatientNotFoundInController() throws Exception {
        List<Long> invalidPatientIds = List.of(999L);

        mockMvc.perform(get("/api/billing/invoice")
                        .param("patients", invalidPatientIds.stream()
                                .map(String::valueOf)
                                .toArray(String[]::new)))
                .andExpect(jsonPath("$.message").value("No patients found for the provided IDs"))
                .andExpect(jsonPath("$.status").value(404));
    }


}