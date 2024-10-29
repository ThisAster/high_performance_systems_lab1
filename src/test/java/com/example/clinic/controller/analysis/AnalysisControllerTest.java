package com.example.clinic.controller.analysis;

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
class AnalysisControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void createAnalysis(@Value("classpath:/analyses/create.json") Resource json) throws Exception {

        mockMvc.perform(post("/api/analyses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.getContentAsByteArray())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    void updateAnalysis(@Value("classpath:/analyses/update.json") Resource json) throws Exception {
        Long analysisId = 2L;

        mockMvc.perform(put("/api/analyses/{id}", analysisId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.getContentAsByteArray())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteAnalysis() throws Exception {
        Long analysisId = 1L;

        mockMvc.perform(delete("/api/analyses/{id}", analysisId))
                .andExpect(status().isOk())
                .andExpect(content().string("Analysis with id " + analysisId + " successfully deleted."));
    }

    @Test
    void getAnalysisById() throws Exception {
        Long analysisId = 1L;

        mockMvc.perform(get("/api/analyses/{id}", analysisId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(analysisId));
    }

    @Test
    void getAnalyses() throws Exception {
        int page = 0;
        int size = 2;

        mockMvc.perform(get("/api/analyses")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(size));
    }
}