package com.example.clinic.document;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
class DocumentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void createAppointment(@Value("classpath:/documents/create.json") Resource json) throws Exception {

        Long patientId = 1L;

        mockMvc.perform(post("/api/documents")
                        .param("patientId", patientId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.getContentAsByteArray())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    void updateDocument(@Value("classpath:/documents/update.json") Resource json) throws Exception {
        Long documentsId = 2L;

        mockMvc.perform(put("/api/documents/{id}", documentsId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.getContentAsByteArray())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteDocument() throws Exception {
        Long documentsId = 1L;

        mockMvc.perform(delete("/api/documents/{id}", documentsId))
                .andExpect(status().isOk())
                .andExpect(content().string("Document with id " + documentsId + " successfully deleted."));
    }

    @Test
    void getDocumentById() throws Exception {
        Long documentsId = 2L;

        mockMvc.perform(get("/api/documents/{id}", documentsId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(documentsId));
    }

    @Test
    void getDocument() throws Exception {
        int page = 0;
        int size = 2;

        mockMvc.perform(get("/api/documents")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(size));
    }
}