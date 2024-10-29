package com.example.clinic.controller.appointment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@AutoConfigureMockMvc
//@Sql(value = {
//    "/appointments/init.sql" Вот тут можно вставить скрипты, но у нас уже есть в миграции
//}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class AppointmentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void createAppointment(@Value("classpath:/appointments/create.json") Resource json) throws Exception {

        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.getContentAsByteArray())
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());

    }

    @Test
    void updateAppointment(@Value("classpath:/appointments/update.json") Resource json) throws Exception {
        Long appointmentId = 2L;

        mockMvc.perform(put("/api/appointments/{id}", appointmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.getContentAsByteArray())
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    void deleteAppointment() throws Exception {
        Long appointmentId = 1L;

        mockMvc.perform(delete("/api/appointments/{id}", appointmentId))
                .andExpect(status().isOk())
                .andExpect(content().string("Appointment with id " + appointmentId + " successfully deleted."));
    }

    @Test
    void getAppointmentById() throws Exception {
        Long appointmentId = 1L; // Replace with actual ID

        mockMvc.perform(get("/api/appointments/{id}", appointmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(appointmentId));
    }

    @Test
    void getAppointments() throws Exception {
        int page = 0;
        int size = 2;

        mockMvc.perform(get("/api/appointments")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(size));
    }
}