package com.example.clinic.mapper;

import org.springframework.stereotype.Component;

import com.example.clinic.dto.AnalysisDto;
import com.example.clinic.entity.Analysis;

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

    public Analysis analysisDtoToEntity(AnalysisDto analysisDto) {
        if (analysisDto == null) {
            return null;
        }

        Analysis analysis = new Analysis();
        analysis.setId(analysisDto.id());
        analysis.setType(analysisDto.type());
        analysis.setSampleDate(analysisDto.sampleDate());
        analysis.setResult(analysisDto.result());
        analysis.setStatus(analysisDto.status());

        return analysis;
    }
}