package com.example.clinic.billing.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DtoTest {

    @Test
    public void testAppointmentDtoData() {
        DoctorDto doctorDto = new DoctorDto(1L, "Merita", "GP");
        AppointmentTypeDTO appointmentTypeDTO = new AppointmentTypeDTO(
                1L,"appointmentTypeName", "Descr", 60,  BigDecimal.valueOf(99), doctorDto);

        AppointmentDto appointmentDto1 = new AppointmentDto(1L,
                LocalDateTime.now().plusDays(1), 1L, appointmentTypeDTO);

        AppointmentDto appointmentDto2 = new AppointmentDto();
        appointmentDto2.setId(2L);
        appointmentDto2.setPatientId(2L);
        appointmentDto2.setAppointmentDate(LocalDateTime.now().plusDays(2));
        appointmentDto2.setAppointmentType(appointmentTypeDTO);

        Assertions.assertNotEquals(appointmentDto1, appointmentDto2);
        Assertions.assertFalse(appointmentDto1.equals(appointmentDto2));
        Assertions.assertTrue((appointmentDto1.hashCode() != appointmentDto2.hashCode())
                || (appointmentDto1.hashCode() == appointmentDto2.hashCode()));
        Assertions.assertNotEquals(appointmentDto1.toString(), appointmentDto2.toString());

        Assertions.assertNotEquals(appointmentDto1.getId(), appointmentDto2.getId());
        Assertions.assertNotEquals(appointmentDto1.getPatientId(), appointmentDto2.getPatientId());
        Assertions.assertNotEquals(appointmentDto1.getAppointmentDate(), appointmentDto2.getAppointmentDate());
        Assertions.assertEquals(appointmentDto1.getAppointmentType(), appointmentDto2.getAppointmentType());
    }

    @Test
    public void testConsultationDTO() {
        DoctorDto doctorDto = new DoctorDto(1L, "Merita", "GP");
        AppointmentTypeDTO appointmentTypeDTO = new AppointmentTypeDTO(
                1L,"appointmentTypeName", "Descr", 60,  BigDecimal.valueOf(99), doctorDto);

        AppointmentDto appointmentDto = new AppointmentDto(1L,
                LocalDateTime.now().plusDays(1), 1L, appointmentTypeDTO);

        List<AppointmentDto> appointmentList = new ArrayList<>();
        appointmentList.add(appointmentDto);

        PatientDto patientDto = new PatientDto(1L,
                "patient", LocalDate.now().minusYears(31), "mail1@mail.ru", appointmentList);

        ConsultationDTO dto1 = new ConsultationDTO();
        dto1.setDoctor(doctorDto);
        dto1.setPatient(patientDto);
        dto1.setPrice(BigDecimal.valueOf(99));

        ConsultationDTO dto2 = new ConsultationDTO();
        dto2.setDoctor(doctorDto);
        dto2.setPatient(patientDto);
        dto2.setPrice(BigDecimal.valueOf(99));

        Assertions.assertTrue(dto1.equals(dto2));
        Assertions.assertEquals(dto1, dto2);
        Assertions.assertTrue((dto1.hashCode() == dto2.hashCode()));
        Assertions.assertEquals(dto1.toString(), dto2.toString());

        Assertions.assertEquals(dto1.getDoctor(), dto2.getDoctor());
        Assertions.assertEquals(dto2.getPatient(), dto2.getPatient());
        Assertions.assertEquals(dto2.getPrice(), dto2.getPrice());
    }

    @Test
    public void testInvoiceDTO() {
        DoctorDto doctorDto = new DoctorDto(1L, "Merita", "GP");
        AppointmentTypeDTO appointmentTypeDTO = new AppointmentTypeDTO(
                1L,"appointmentTypeName", "Descr", 60,  BigDecimal.valueOf(99), doctorDto);

        AppointmentDto appointmentDto = new AppointmentDto(1L,
                LocalDateTime.now().plusDays(1), 1L, appointmentTypeDTO);

        List<AppointmentDto> appointmentList = new ArrayList<>();
        appointmentList.add(appointmentDto);

        PatientDto patientDto = new PatientDto(1L,
                "patient", LocalDate.now().minusYears(31), "mail1@mail.ru", appointmentList);

        ConsultationDTO dto1 = new ConsultationDTO();
        dto1.setDoctor(doctorDto);
        dto1.setPatient(patientDto);
        dto1.setPrice(BigDecimal.valueOf(11));

        ConsultationDTO dto2 = new ConsultationDTO();
        dto2.setDoctor(doctorDto);
        dto2.setPatient(patientDto);
        dto2.setPrice(BigDecimal.valueOf(22));

        List<ConsultationDTO> list = new ArrayList<>();
        InvoiceDTO invoiceDTO1 = InvoiceDTO.builder()
                .consultations(list)
                .totalCost(BigDecimal.valueOf(111))
                .build();

        InvoiceDTO invoiceDTO2 = InvoiceDTO.builder()
                .consultations(list)
                .totalCost(BigDecimal.valueOf(222))
                .build();

        Assertions.assertFalse(invoiceDTO1.equals(invoiceDTO2));
        Assertions.assertNotEquals(invoiceDTO1, invoiceDTO2);
        Assertions.assertTrue((invoiceDTO1.hashCode() != invoiceDTO2.hashCode())
                || (invoiceDTO1.hashCode() == invoiceDTO2.hashCode()));
        Assertions.assertNotEquals(invoiceDTO1.toString(), invoiceDTO2.toString());

        Assertions.assertEquals(invoiceDTO1.getConsultations().size(), invoiceDTO2.getConsultations().size());
        Assertions.assertNotEquals(invoiceDTO1.getTotalCost(), invoiceDTO2.getTotalCost());
    }

}
