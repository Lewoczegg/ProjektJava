package com.mewocz.storeeverything.controllers;


import com.mewocz.storeeverything.model.Category;
import com.mewocz.storeeverything.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category/addCategory")
    public String addCategoryForm(Model model){
        Category category = new Category();

        model.addAttribute("category", category);

        return "/category/categoryForm";
    }

    @PostMapping("category/saveCategory")
    public String saveCategory(@Valid @ModelAttribute("category") Category category,
                               BindingResult theBindingResult){

        if (theBindingResult.hasErrors()){

            return "/category/categoryForm";
        }

        categoryService.save(category);

        return "redirect:/info/addInfo";
    }


}
