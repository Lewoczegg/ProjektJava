package com.mewocz.storeeverything.controllers;

import com.mewocz.storeeverything.dto.UserRequest;
import com.mewocz.storeeverything.model.User;
import com.mewocz.storeeverything.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        UserRequest user = new UserRequest();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserRequest userRequest,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByLogin(userRequest.getLogin());

        if(existingUser != null && existingUser.getLogin() != null && !existingUser.getLogin().isEmpty()){
            result.rejectValue("login", "NOT_UNIQUE_LOGIN",
                    "There is already an account registered with the same login");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userRequest);
            return "/register";
        }

        userService.saveUser(userRequest);
        return "redirect:/register?success";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}

