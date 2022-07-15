package com.training.istasenka.converter.category;

import com.training.istasenka.dto.CategoryDto;
import com.training.istasenka.model.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryConverter {

    CategoryConverter INSTANCE = Mappers.getMapper(CategoryConverter.class);

    CategoryDto convertCategoryToCategoryDto(Category category);

    Category convertCategoryDtoToCategory(CategoryDto categoryDto);
}
