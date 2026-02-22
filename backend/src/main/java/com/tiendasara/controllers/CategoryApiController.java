package com.tiendasara.controllers;

import com.tiendasara.models.Category;
import com.tiendasara.services.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API Controller para Categorías.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryApiController {

    private final CategoryRepository categoryRepository;

    public CategoryApiController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("")
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
