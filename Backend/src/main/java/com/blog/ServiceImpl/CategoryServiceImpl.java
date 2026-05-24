package com.blog.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.Repositories.CategoryRepo;
import com.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    // Create Category
    @Override
    public CategoryDto createCategory(
            CategoryDto categoryDto) {

        Category category =
                this.modelMapper.map(
                        categoryDto,
                        Category.class
                );

        Category savedCategory =
                this.categoryRepo.save(category);

        return this.modelMapper.map(
                savedCategory,
                CategoryDto.class
        );
    }

    // Update Category
    @Override
    public CategoryDto updateCategory(
            CategoryDto categoryDto,
            Integer categoryId) {

        Category category =
                this.categoryRepo.findById(categoryId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Category",
                                        "id",
                                        categoryId
                                )
                        );

        category.setCategoryTitle(
                categoryDto.getCategoryTitle()
        );

        category.setCategoryDescription(
                categoryDto.getCategoryDescription()
        );

        Category updatedCategory =
                this.categoryRepo.save(category);

        return this.modelMapper.map(
                updatedCategory,
                CategoryDto.class
        );
    }

    // Delete Category
    @Override
    public void deleteCategory(
            Integer categoryId) {

        Category category =
                this.categoryRepo.findById(categoryId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Category",
                                        "id",
                                        categoryId
                                )
                        );

        this.categoryRepo.delete(category);
    }

    // Get Single Category
    @Override
    public CategoryDto getCategoryById(
            Integer categoryId) {

        Category category =
                this.categoryRepo.findById(categoryId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Category",
                                        "id",
                                        categoryId
                                )
                        );

        return this.modelMapper.map(
                category,
                CategoryDto.class
        );
    }

    // Get All Categories
    @Override
    public List<CategoryDto> getAllCategories() {

        List<Category> categories =
                this.categoryRepo.findAll();

        List<CategoryDto> categoryDtos =
                categories.stream()
                        .map(category ->
                                this.modelMapper.map(
                                        category,
                                        CategoryDto.class
                                )
                        )
                        .collect(Collectors.toList());

        return categoryDtos;
    }
}