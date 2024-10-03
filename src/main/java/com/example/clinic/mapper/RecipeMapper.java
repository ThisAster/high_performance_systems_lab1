package com.example.clinic.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.clinic.dto.RecipeDto;
import com.example.clinic.entity.Recipe;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RecipeMapper {

    Recipe recipeDtoToEntity(RecipeDto recipeDto);

    void updateEntityFromDto(@MappingTarget Recipe entity, RecipeDto dto);

    RecipeDto entityToRecipeDto(Recipe entity);
}
