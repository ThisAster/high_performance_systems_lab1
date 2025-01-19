package com.example.clinic.timetable.integration.feign;

import com.example.clinic.timetable.dto.AppointmentDto;
import com.example.clinic.timetable.integration.AppointmentService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Profile("!test")
@FeignClient(name = "appointment-client", url = "${appointment.service.url}")
public interface AppointmentClient extends AppointmentService {
    @Override
    @GetMapping("/filter")
    List<AppointmentDto> getAppointmentsByDoctorIdAndTimeInterval(
            @RequestParam Long doctor,
            @RequestParam LocalDateTime appointmentTimeFrom,
            @RequestParam LocalDateTime appointmentTimeTo
    );
}
