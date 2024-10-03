package com.example.clinic.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.clinic.dto.AnalysisDto;
import com.example.clinic.entity.Analysis;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AnalysisMapper {

    Analysis analysisDtoToEntity(AnalysisDto analysisDto);

    void updateEntityFromDto(@MappingTarget Analysis entity, AnalysisDto dto);

    AnalysisDto entityToAnalysisDto(Analysis entity);
}
