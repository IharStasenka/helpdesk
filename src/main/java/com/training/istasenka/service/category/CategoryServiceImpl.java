package com.training.istasenka.service.category;

import com.training.istasenka.model.category.Category;
import com.training.istasenka.repository.category.CategoryRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Cacheable(cacheNames = "category.cache", key = "#categoryName")
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.
                findByName(categoryName)
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("There are no category with name %s.", categoryName)));
    }
}
