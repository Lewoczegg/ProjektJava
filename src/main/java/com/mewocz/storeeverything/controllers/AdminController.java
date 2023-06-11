package com.mewocz.storeeverything.controllers;

import com.mewocz.storeeverything.model.Role;
import com.mewocz.storeeverything.model.User;
import com.mewocz.storeeverything.services.UserService;
import com.mewocz.storeeverything.services.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    public AdminController(UserServiceImpl userService){this.userService = userService;}

    @GetMapping("/list")
    public String listUsers(Model theModel){

        List<User> userList = userService.findAllUsers();

        theModel.addAttribute("users", userList);

        return "admin/usersList";

    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user){

        userService.save(user);

        return "redirect:/admin/list";
    }

    @GetMapping("/showFormForUpdate/{userId}")
    public String showFormForUpdate(@PathVariable("userId") Long id, Model model){

        User user = userService.findById(id);

        List<Role> roleList = userService.findRoles();

        model.addAttribute("user", user);

        model.addAttribute("listRoles", roleList);

        return "admin/userForm";
    }
}
