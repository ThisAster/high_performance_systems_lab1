package com.example.clinic.controller.billing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {
        "/sql/test.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class BillingControllerTest {



    @Autowired
    MockMvc mockMvc;


    @Test
    void getAppointments() throws Exception {
        int patientId = 2;

        mockMvc.perform(get("/api/billing/invoice")
                        .param("patients", String.valueOf(patientId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total_cost").value("1000.0"))
                .andExpect(jsonPath("$.consultations.length()").value(2));
    }

}