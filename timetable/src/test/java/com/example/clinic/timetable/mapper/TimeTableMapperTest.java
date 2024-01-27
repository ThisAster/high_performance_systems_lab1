package com.example.clinic.timetable.mapper;

import com.example.clinic.timetable.dto.AppointmentDto;
import com.example.clinic.timetable.dto.AppointmentTypeDTO;
import com.example.clinic.timetable.dto.DoctorDto;
import com.example.clinic.timetable.dto.TimeTableDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TimeTableMapperTest {

    @InjectMocks
    TimeTableMapper timeTableMapper;

    @Test
    public void map() {
        DoctorDto doctorDto = new DoctorDto(1L, "Merita", "GP");
        AppointmentTypeDTO appointmentTypeDTO = new AppointmentTypeDTO(
                1L,"appointmentTypeName", "Descr", 60,  BigDecimal.valueOf(99), doctorDto);
        AppointmentDto appointmentDto = new AppointmentDto(1L, LocalDateTime.now().plusDays(3), 1L, appointmentTypeDTO);

        List<AppointmentDto> appointmentList = new ArrayList<>();
        appointmentList.add(appointmentDto);
        LocalDate date = LocalDate.now().plusDays(3);
        Long doctorId = 1L;

        TimeTableDTO timeTableDTO = timeTableMapper.mapToDto(date, doctorId, appointmentList);

        Assertions.assertEquals(timeTableDTO.getDoctorId(), doctorId);
        Assertions.assertTrue(timeTableDTO.getDate().equals(date));
        Assertions.assertEquals(timeTableDTO.getAppointmentDtoList().size(), appointmentList.size());
    }

}
