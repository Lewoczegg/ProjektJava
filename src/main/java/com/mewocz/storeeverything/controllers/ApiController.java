package com.mewocz.storeeverything.controllers;

import com.mewocz.storeeverything.services.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService){
        this.apiService = apiService;
    }

    @GetMapping("/{word}")
    @ResponseBody
    public String isWordNoun(@PathVariable("word") String word){
        if(apiService.isANoun(word)){
            return "{\"Is a Noun\"}";
        }
        return "{\"Is not a Noun\"}";
    }
}
