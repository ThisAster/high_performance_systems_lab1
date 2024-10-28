package com.example.clinic.service.analysis;


import com.example.clinic.app.analysis.dto.AnalysisCreationDto;
import com.example.clinic.app.analysis.entity.Analysis;
import com.example.clinic.app.analysis.service.AnalysisService;
import com.example.clinic.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AnalysisServiceTest {

    @Autowired
    private AnalysisService analysisService;

    @Test
    void createAnalysisTest(){
        AnalysisCreationDto analysisCreationDto = new AnalysisCreationDto(
                "poop check",
                LocalDate.of(Integer.parseInt("1984"), Integer.parseInt("4"), Integer.parseInt("20")),
                "smells",
                "done"
        );

        Analysis createdAnalysis = analysisService.createAnalysis(analysisCreationDto, 1L);
        Analysis retrievedAnalysis = analysisService.getAnalysisById(createdAnalysis.getId());

        assertNotNull(retrievedAnalysis);
    }

    @Test
    void getAnalysisByIdTest(){
        AnalysisCreationDto analysisCreationDto = new AnalysisCreationDto(
                "poop check",
                LocalDate.of(Integer.parseInt("1984"), Integer.parseInt("4"), Integer.parseInt("20")),
                "smells",
                "done"
        );

        Analysis createdAnalysis = analysisService.createAnalysis(analysisCreationDto, 1L);
        Analysis retrievedAnalysis = analysisService.getAnalysisById(createdAnalysis.getId());

        assertNotNull(retrievedAnalysis);
        assertEquals(retrievedAnalysis.getId(), createdAnalysis.getId());
        assertEquals(retrievedAnalysis.getType(), createdAnalysis.getType());
        assertEquals(retrievedAnalysis.getSampleDate(), createdAnalysis.getSampleDate());
        assertEquals(retrievedAnalysis.getStatus(), createdAnalysis.getStatus());
    }

    @Test
    void deleteAnalysisTest(){
        AnalysisCreationDto analysisCreationDto = new AnalysisCreationDto(
                "poop check",
                LocalDate.of(Integer.parseInt("1984"), Integer.parseInt("4"), Integer.parseInt("20")),
                "smells",
                "done"
        );
        Analysis createdAnalysis = analysisService.createAnalysis(analysisCreationDto, 1L);
        analysisService.deleteAnalysis(createdAnalysis.getId());
        assertThrows(EntityNotFoundException.class,
                () -> analysisService.getAnalysisById(createdAnalysis.getId()));
    }

    @Test
    void updateAnalysisTest(){
        AnalysisCreationDto oldAnalysisCreationDto = new AnalysisCreationDto(
                "poop check",
                LocalDate.of(Integer.parseInt("1984"), Integer.parseInt("4"), Integer.parseInt("20")),
                "smells",
                "done"
        );

        AnalysisCreationDto newAnalysisCreationDto = new AnalysisCreationDto(
                "poop check",
                LocalDate.of(Integer.parseInt("1984"), Integer.parseInt("4"), Integer.parseInt("20")),
                "doesn't smell",
                "done"
        );

        Analysis createdAnalysis = analysisService.createAnalysis(oldAnalysisCreationDto, 1L);
        Analysis updatedAnalysis = analysisService.updateAnalysis(createdAnalysis.getId(), newAnalysisCreationDto);

        assertNotNull(updatedAnalysis);
        assertEquals(newAnalysisCreationDto.type(), updatedAnalysis.getType());
        assertEquals(newAnalysisCreationDto.sampleDate(), updatedAnalysis.getSampleDate());
        assertEquals(newAnalysisCreationDto.result(), updatedAnalysis.getResult());
        assertEquals(newAnalysisCreationDto.status(), updatedAnalysis.getStatus());

    }
}