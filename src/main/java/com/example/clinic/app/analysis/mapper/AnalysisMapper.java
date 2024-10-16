package com.example.clinic.app.analysis.mapper;

import com.example.clinic.app.analysis.dto.AnalysisCreationDto;
import org.springframework.stereotype.Component;

import com.example.clinic.app.analysis.dto.AnalysisDto;
import com.example.clinic.app.analysis.entity.Analysis;

@Component
public class AnalysisMapper {

    public AnalysisDto entityToAnalysisDto(Analysis analysis) {
        if (analysis == null) {
            return null;
        }
        
        return new AnalysisDto(
            analysis.getId(),
            analysis.getType(),
            analysis.getSampleDate(),
            analysis.getResult(),
            analysis.getStatus()
        );
    }

    public Analysis analysisDtoToEntity(AnalysisCreationDto analysisDto) {
        if (analysisDto == null) {
            return null;
        }

        Analysis analysis = new Analysis();
        analysis.setType(analysisDto.type());
        analysis.setSampleDate(analysisDto.sampleDate());
        analysis.setResult(analysisDto.result());
        analysis.setStatus(analysisDto.status());

        return analysis;
    }
}