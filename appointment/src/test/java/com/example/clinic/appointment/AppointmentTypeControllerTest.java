package com.example.clinic.appointment;

import com.example.clinic.appointment.integration.DoctorService;
import com.example.clinic.appointment.integration.EmailService;
import com.example.clinic.appointment.integration.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {
        "/sql/test.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
class AppointmentTypeControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    DoctorService doctorService;
    @MockBean
    EmailService emailService;
    @MockBean
    PatientService patientService;

    @Test
    void createAppointmentType(@Value("classpath:/appointments/createtype.json") Resource json) throws Exception {

        mockMvc.perform(post("/api/appointments-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.getContentAsByteArray())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    void updateAppointment(@Value("classpath:/appointments/updatetype.json") Resource json) throws Exception {
        Long appointmentId = 2L;

        mockMvc.perform(put("/api/appointments-type/{id}", appointmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.getContentAsByteArray())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteAppointment() throws Exception {
        Long appointmentId = 3L;

        mockMvc.perform(delete("/api/appointments-type/{id}", appointmentId))
                .andExpect(status().isOk())
                .andExpect(content().string("AppointmentsType with id " + appointmentId + " successfully deleted."));
    }

    @Test
    void getAppointmentById() throws Exception {
        Long appointmentId = 1L; // Replace with actual ID

        mockMvc.perform(get("/api/appointments-type/{id}", appointmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(appointmentId));
    }

    @Test
    void getAppointments() throws Exception {
        int page = 0;
        int size = 2;

        mockMvc.perform(get("/api/appointments-type")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(size));
    }
}