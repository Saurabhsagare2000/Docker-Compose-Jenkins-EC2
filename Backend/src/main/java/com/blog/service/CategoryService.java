package com.blog.service;

import java.util.List;

import com.blog.payloads.CategoryDto;

public interface CategoryService {

    // Create Category
    CategoryDto createCategory(CategoryDto categoryDto);

    // Update Category
    CategoryDto updateCategory(
            CategoryDto categoryDto,
            Integer categoryId
    );

    // Delete Category
    void deleteCategory(Integer categoryId);

    // Get Single Category
    CategoryDto getCategoryById(
            Integer categoryId
    );

    // Get All Categories
    List<CategoryDto> getAllCategories();
}