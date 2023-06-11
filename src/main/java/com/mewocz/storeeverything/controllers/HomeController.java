package com.mewocz.storeeverything.controllers;

import com.mewocz.storeeverything.model.Category;
import com.mewocz.storeeverything.model.Information;
import com.mewocz.storeeverything.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class HomeController {


    @GetMapping({"", "/"})
    public String goToInfo(){
        return "redirect:/info/";
    }
}
