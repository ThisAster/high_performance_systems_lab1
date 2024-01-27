package com.example.clinic.billing.integration.feign;

import com.example.clinic.billing.dto.AppointmentDto;
import com.example.clinic.billing.dto.AppointmentTypeDTO;
import com.example.clinic.billing.dto.DoctorDto;
import com.example.clinic.billing.dto.PatientDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientClientTest {

    @Mock
    PatientFeignClient patientFeignClient;

    @Mock
    AppointmentFeignClient appointmentFeignClient;

    @InjectMocks
    PatientClient patientClient;

    @Test
    public void test1() {
        // appointment 1
        DoctorDto doctorDto = new DoctorDto(1L, "Merita", "GP");
        AppointmentTypeDTO appointmentTypeDTO = new AppointmentTypeDTO(
                1L,"appointmentTypeName", "Descr", 60,  BigDecimal.valueOf(99), doctorDto);

        AppointmentDto appointmentDto = new AppointmentDto(1L,
                LocalDateTime.now().plusDays(1), 1L, appointmentTypeDTO);

        List<AppointmentDto> appointmentList = new ArrayList<>();
        appointmentList.add(appointmentDto);

        // patient 1
        PatientDto patientDto = new PatientDto(1L,
                "patient", LocalDate.now().minusYears(31), "mail1@mail.ru", appointmentList);

        List<PatientDto> patientDtoList = new ArrayList<>();
        patientDtoList.add(patientDto);

        List<Long> patientIdSet = new ArrayList<>();
        patientIdSet.add(1L);

        when(patientFeignClient.findByIds(patientIdSet)).thenReturn(patientDtoList);
        when(appointmentFeignClient.findByPatients(patientIdSet)).thenReturn(appointmentList);

        List<PatientDto> list = patientClient.getPatientsWithAppointmentsByIds(patientIdSet);

        assertEquals(1, list.size());
    }

}
