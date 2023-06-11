package com.mewocz.storeeverything.services;

import com.mewocz.storeeverything.model.Category;
import com.mewocz.storeeverything.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){this.categoryRepository = categoryRepository;}

    public Category save(Category category)
    {
        return categoryRepository.save(category);
    }

    public ArrayList<Category> getCategories()
    {
        return (ArrayList<Category>) categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(String id){
        return categoryRepository.findById(Long.valueOf(id));
    }
}
