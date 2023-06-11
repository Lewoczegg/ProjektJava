package com.mewocz.storeeverything.controllers;

import com.mewocz.storeeverything.dto.ShareRequest;
import com.mewocz.storeeverything.model.Information;
import com.mewocz.storeeverything.model.User;
import com.mewocz.storeeverything.services.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

import com.mewocz.storeeverything.model.Category;

@Controller
@RequestMapping("/info")
public class InformationController {

    private final InformationService informationService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final SharedInformationsService sharedInformationsService;


    @Autowired
    public InformationController(InformationService informationService, CategoryService categoryService, UserServiceImpl userService, SharedInformationsService sharedInformationsService){
        this.informationService = informationService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.sharedInformationsService = sharedInformationsService;
    }

    @GetMapping("/shareForm/{informationId}")
    public String shareInformationForm(@AuthenticationPrincipal UserDetails loggedUser,
                                       @PathVariable("informationId") Long id,
                                       Model model){
        String email = loggedUser.getUsername();
        User user = userService.findUserByLogin(email);

        Information information = informationService.getInformationById(id);

        if(!Objects.equals(user.getId(), information.getUser().getId())){
            return "/informations/main";
        }

        ShareRequest shareRequest = new ShareRequest();
        shareRequest.setId(id);

        model.addAttribute("share", shareRequest);

        return "informations/shareNote";
    }

    @PostMapping("/share")
    public String shareNoteToUser(@AuthenticationPrincipal UserDetails loggedUser,
                                  @ModelAttribute("share") ShareRequest shareRequest,
                                  BindingResult result){

        String login = loggedUser.getUsername();
        User user = userService.findUserByLogin(login);

        Information information = informationService.getInformationById(shareRequest.getId());

        if(!Objects.equals(user.getId(), information.getUser().getId())){
            return "main";
        }

        User targetUser = userService.findUserByLogin(shareRequest.getUserName());

        if(targetUser == null){
            result.rejectValue("userName", "noUser",
                    "There is no user with this Username");
        }

        if(result.hasErrors()){
            return "informations/shareNote";
        }

        informationService.shareNote(shareRequest.getId(), targetUser);

        return "redirect:/info/";
    }

    @GetMapping("/shared")
    public String sharedPage(@AuthenticationPrincipal UserDetails loggedUser,
                             Model model){

        String email = loggedUser.getUsername();
        User user = userService.findUserByLogin(email);

        ArrayList<Information> informations = sharedInformationsService.getSharedInformationsForUser(user);

        model.addAttribute("informations", informations);

        return "informations/sharedInformations";
    }

    @GetMapping("/shared/{uuid}")
    public String sharedPage(Model model,
                             @PathVariable("uuid") String uuid){

        Information information = informationService.getInformationByUUID(uuid);
        ArrayList<Information> informations = new ArrayList<>();
        informations.add(information);

        model.addAttribute("informations", informations);

        return "informations/sharedInformations";
    }

    @GetMapping("/")
    public String informationsPage(@AuthenticationPrincipal UserDetails loggedUser,
                                   @RequestParam(required = false, name = "sortField") String sortField,
                                   @RequestParam(required = false, name = "sortDirection", value = "sortDirection") String sortDirection,
                                   @RequestParam(required = false, name = "filterDateStart", value = "") LocalDate filterDateStart,
                                   @RequestParam(required = false, name = "filterDateEnd", value = "") LocalDate filterDateEnd,
                                   @CookieValue(value = "sortFieldCookie", defaultValue = "title") String sortFieldCookie,
                                   @CookieValue(value = "sortDirectionCookie", defaultValue = "asc") String sortDirectionCookie,
                                   HttpServletResponse response,
                                   Model model) {

        String email = loggedUser.getUsername();
        User user = userService.findUserByLogin(email);

        if(sortField == null){
            sortField = sortFieldCookie;
        }

        if(sortDirection == null){
            sortDirection = sortDirectionCookie;
        }

        Cookie cookie = new Cookie("sortFieldCookie", sortField);
        Cookie cookie2 = new Cookie("sortDirectionCookie", sortDirection);

        response.addCookie(cookie);
        response.addCookie(cookie2);

        List<Information> informations = new ArrayList<>();
        if(sortField.equals("category")){
            informations = informationService.getInformationByPopularityForUser(user.getId());
        } else {
            informations = informationService.findAllByUserId(user.getId(), sortField, sortDirection);
        }

        if(filterDateStart != null && filterDateEnd != null){
            List<Information> informationsTemp = new ArrayList<>();
            for(Information info: informations){
                if(info.getCreationDate().isBefore(filterDateEnd) && info.getCreationDate().isAfter(filterDateStart) || info.getCreationDate().isEqual(filterDateStart) || info.getCreationDate().isEqual(filterDateEnd)){
                    informationsTemp.add(info);
                }
            }
            informations = informationsTemp;
        }


        ArrayList<Category> allCategories = categoryService.getCategories();

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);

        model.addAttribute("informations", informations);
        model.addAttribute("categories", allCategories);
        return "/informations/main";
    }

    @GetMapping("/forToday")
    public String forToday(@AuthenticationPrincipal UserDetails loggedUser,
                                   Model model) {

        String email = loggedUser.getUsername();
        User user = userService.findUserByLogin(email);

        List<Information> informations = informationService.getInformationWithTodaysReminderDate(user.getId());

        model.addAttribute("informations", informations);
        return "/informations/forToday";
    }


    @GetMapping("/addInfo")
    public String showFormForAdd(Model theModel) {

        Information information = new Information();

        List<Category> categoryList = categoryService.getCategories();

        theModel.addAttribute("information", information);
        theModel.addAttribute("categoryList", categoryList);

        return "/informations/informationForm";
    }

    @GetMapping("/updateInfo/{informationId}")
    public String showFormForUpdate(@AuthenticationPrincipal UserDetails loggedUser, @PathVariable("informationId") Long id,
                                    Model theModel) {
        String email = loggedUser.getUsername();
        User user = userService.findUserByLogin(email);

        Information information = informationService.getInformationById(id);

        if(user != information.getUser()){
            return "/access-denied";
        }

        List<Category> categoryList = categoryService.getCategories();

        theModel.addAttribute("information", information);
        theModel.addAttribute("categoryList", categoryList);

        return "/informations/informationForm";
    }

    @PostMapping("/save")
    public String saveInformation(@AuthenticationPrincipal UserDetails loggedUser,
                                  @Valid @ModelAttribute("information") Information information,
                                  BindingResult theBindingResult, Model theModel) throws ParseException {

        String login = loggedUser.getUsername();
        User user = userService.findUserByLogin(login);

        if(information.getCreationDate()==null) {
            LocalDate date = LocalDate.now();
            information.setCreationDate(date);
        }
        System.out.println(information.getReminderDate());

        information.setUser(user);

        if (theBindingResult.hasErrors()){
            List<Category> categoryList = categoryService.getCategories();
            theModel.addAttribute("categoryList", categoryList);
            return "/informations/informationForm";
        }

        informationService.save(information);

        return "redirect:/info/";
    }

    @GetMapping("/deleteInfo/{informationId}")
    public String delete(@PathVariable("informationId") Long id) {

        informationService.deleteInformation(id);

        return "redirect:/info/";

    }

    @GetMapping("/shareLink/{informationId}")
    public String shareLink(@PathVariable("informationId") Long id) {

        informationService.generateUUID(id);

        return "redirect:/info/";

    }

}
