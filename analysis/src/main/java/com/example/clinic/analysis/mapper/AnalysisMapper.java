package com.example.clinic.analysis.mapper;

import com.example.clinic.analysis.dto.AnalysisCreationDto;
import com.example.clinic.analysis.dto.AnalysisDto;
import com.example.clinic.analysis.entity.Analysis;
import org.springframework.stereotype.Component;

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