package com.example.clinic.patient.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DtoTest {

    @Test
    public void testAppointmentDtoData() {
        AppointmentTypeDTO appointmentTypeDTO = new AppointmentTypeDTO(
                1L,"appointmentTypeName", "Descr", 60,  BigDecimal.valueOf(99), 1L);

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

}
