package com.blog.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;
import com.blog.service.CategoryService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Create Category
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(
            @RequestBody CategoryDto categoryDto) {

        CategoryDto createdCategory =
                this.categoryService.createCategory(
                        categoryDto
                );

        return new ResponseEntity<>(
                createdCategory,
                HttpStatus.CREATED
        );
    }

    // Update Category
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @RequestBody CategoryDto categoryDto,
            @PathVariable Integer categoryId) {

        CategoryDto updatedCategory =
                this.categoryService.updateCategory(
                        categoryDto,
                        categoryId
                );

        return new ResponseEntity<>(
                updatedCategory,
                HttpStatus.OK
        );
    }

    // Delete Category
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(
            @PathVariable Integer categoryId) {

        this.categoryService.deleteCategory(
                categoryId
        );

        return new ResponseEntity<ApiResponse>(
                new ApiResponse( "Category deleted successfully !!",true ),HttpStatus.OK);
    }

    // Get Single Category
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(
            @PathVariable Integer categoryId) {

        CategoryDto categoryDto =
                this.categoryService.getCategoryById(
                        categoryId
                );

        return new ResponseEntity<>(
                categoryDto,
                HttpStatus.OK
        );
    }

    // Get All Categories
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {

        List<CategoryDto> categories =
                this.categoryService.getAllCategories();

        return new ResponseEntity<>(
                categories,
                HttpStatus.OK
        );
    }
}